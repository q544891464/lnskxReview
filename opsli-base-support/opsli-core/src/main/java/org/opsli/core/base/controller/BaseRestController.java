/**
 * Copyright 2020 OPSLI 快速开发平台 https://www.opsli.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.opsli.core.base.controller;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.opsli.api.base.result.ResultVo;
import org.opsli.api.base.warpper.ApiWrapper;
import org.opsli.api.wrapper.system.user.UserModel;
import org.opsli.common.annotation.RequiresPermissionsCus;
import org.opsli.common.annotation.hotdata.EnableHotData;
import org.opsli.common.constants.TreeConstants;
import org.opsli.common.enums.ExcelOperate;
import org.opsli.common.exception.ServiceException;
import org.opsli.common.exception.TokenException;
import org.opsli.common.utils.OutputStreamUtil;
import org.opsli.common.utils.WrapperUtil;
import org.opsli.core.autoconfigure.properties.GlobalProperties;
import org.opsli.core.base.entity.BaseEntity;
import org.opsli.core.base.entity.HasChildren;
import org.opsli.core.base.service.interfaces.CrudServiceInterface;
import org.opsli.core.msg.CoreMsg;
import org.opsli.core.msg.TokenMsg;
import org.opsli.core.security.shiro.realm.JwtRealm;
import org.opsli.core.utils.ExcelUtil;
import org.opsli.core.utils.UserUtil;
import org.opsli.plugins.excel.exception.ExcelPluginException;
import org.opsli.plugins.excel.listener.BatchExcelListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

/**
 * 默认 范型引用 子类的Service ， 为简单的CRUD做足准备
 *
 * @author Parker
 * @date 2020-09-15
 */
@Slf4j
@RestController
public abstract class BaseRestController <T extends BaseEntity, E extends ApiWrapper, S extends CrudServiceInterface<T,E>>{

    /** 开启热点数据状态 */
    protected boolean hotDataFlag = false;

    /** Entity Clazz 类 */
    protected Class<T> entityClazz;
    /** Model Clazz 类 */
    protected Class<E> modelClazz;


    /** 配置类 */
    @Autowired
    protected GlobalProperties globalProperties;

    /** 子类Service */
    @Autowired(required = false)
    protected S IService;

    /**
     * Excel 导入
     * @param request request
     * @return ResultVo
     */
    protected ResultVo<?> importExcel(MultipartHttpServletRequest request){
        // 计时器
        TimeInterval timer = DateUtil.timer();
        Iterator<String> itr = request.getFileNames();
        String uploadedFile = itr.next();
        List<MultipartFile> files = request.getFiles(uploadedFile);

        if (CollectionUtils.isEmpty(files)) {
            // 请选择文件
            return ResultVo.error(CoreMsg.EXCEL_FILE_NULL.getCode(),
                    CoreMsg.EXCEL_FILE_NULL.getMessage());
        }
        ResultVo<?> resultVo ;
        String msgInfo;
        try {
            UserModel user = UserUtil.getUser();
            Date currDate = DateUtil.date();

            // 导入优化为 监听器 模式 超过一定阈值直接释放资源 防止导入数据导致系统 OOM
            ExcelUtil.getInstance().readExcelByListener(files.get(0), modelClazz, new BatchExcelListener<E>() {
                @Override
                public void saveData(List<E> dataList) {
                    // 处理字典数据
                    List<E> disposeData = ExcelUtil.getInstance().handleDatas(dataList, modelClazz, ExcelOperate.READ);
                    // 手动赋值 必要数据 防止频繁开启Redis网络IO
                    for (E model : disposeData) {
                        model.setIzManual(true);
                        model.setCreateBy(user.getId());
                        model.setUpdateBy(user.getId());
                        model.setCreateTime(currDate);
                        model.setUpdateTime(currDate);
                    }
                    // 数据库插入数据
                    IService.insertBatch(disposeData);
                }
            });

            // 花费毫秒数
            long timerCount = timer.interval();
            // 提示信息
            msgInfo = StrUtil.format(CoreMsg.EXCEL_IMPORT_SUCCESS.getMessage(), DateUtil.formatBetween(timerCount));
            // 导出成功
            resultVo = ResultVo.success(msgInfo);
            resultVo.setCode(CoreMsg.EXCEL_IMPORT_SUCCESS.getCode());

        } catch (ExcelPluginException e) {
            // 花费毫秒数
            long timerCount = timer.interval();
            // 提示信息
            msgInfo = StrUtil.format(CoreMsg.EXCEL_IMPORT_ERROR.getMessage(), DateUtil.formatBetween(timerCount),
                    e.getMessage());
            // 导入失败
            resultVo = ResultVo.error(CoreMsg.EXCEL_IMPORT_ERROR.getCode(), msgInfo);
        }
        // 记录导出日志
        log.info(msgInfo);
        return resultVo;
    }

    /**
     * 下载导入模板
     * @param fileName 文件名称
     * @param response response
     */
    protected void importTemplate(String fileName, HttpServletResponse response, Method method){
        this.excelExport(fileName + " 模版 ",null, response, method);
    }

    /**
     * 导出
     *
     * 导出时，Token认证和方法权限认证 全部都由自定义完成
     * 因为在 导出不成功时，需要推送错误信息，
     * 前端直接走下载流，当失败时无法获得失败信息，即使前后端换一种方式后端推送二进制文件前端再次解析也是最少2倍的耗时
     * ，且如果数据量过大，前端进行渲染时直接会把浏览器卡死
     * 而直接开启socket接口推送显然是太过浪费资源了，所以目前采用Java最原始的手段
     * response 推送 javascript代码 alert 提示报错信息
     *
     * @param fileName 文件名称
     * @param queryWrapper 查询构建器
     * @param response response
     */
    protected void excelExport(String fileName, QueryWrapper<T> queryWrapper, HttpServletResponse response,
                                      Method method){
        // 权限认证
        try {
            if(method == null){
                // 无权访问该方法
                throw new TokenException(TokenMsg.EXCEPTION_NOT_AUTH);

            }

            // Token 认证
            JwtRealm.authToken();

            RequiresPermissionsCus permissionsCus = method.getAnnotation(RequiresPermissionsCus.class);
            if(permissionsCus != null){
                // 方法权限认证
                JwtRealm.authPerms(permissionsCus.value());
            }
        }catch (TokenException e){
            // 推送错误信息
            OutputStreamUtil.exceptionResponse(e.getMessage(), response);
            return;
        }

        // 计时器
        TimeInterval timer = DateUtil.timer();
        String msgInfo;
        ResultVo<?> resultVo;
        List<E> modelList = Lists.newArrayList();
        try {
            if(queryWrapper != null){
                // 导出数量限制 -1 为无限制
                Integer exportMaxCount = globalProperties.getExcel().getExportMaxCount();
                if(exportMaxCount != null && exportMaxCount > -1){
                    // 获得数量 大于 阈值 禁止导出， 防止OOM
                    int count = IService.count(queryWrapper);
                    if(count > exportMaxCount){
                        String maxError = StrUtil.format(CoreMsg.EXCEL_HANDLE_MAX.getMessage(), count,
                                exportMaxCount);
                        // 超出最大导出数量
                        throw new ExcelPluginException(CoreMsg.EXCEL_HANDLE_MAX.getCode(), maxError);
                    }
                }


                List<T> entityList = IService.findList(queryWrapper);
                // 转化类型
                modelList = WrapperUtil.transformInstance(entityList, modelClazz);
            }
            // 导出Excel
            ExcelUtil.getInstance().writeExcel(response, modelList ,fileName,"sheet", modelClazz ,ExcelTypeEnum.XLSX);
            // 花费毫秒数
            long timerCount = timer.interval();
            // 提示信息
            msgInfo = StrUtil.format(CoreMsg.EXCEL_EXPORT_SUCCESS.getMessage(), modelList.size(),
                    DateUtil.formatBetween(timerCount));
            // 导出成功
            resultVo = ResultVo.success(msgInfo);
            resultVo.setCode(CoreMsg.EXCEL_EXPORT_SUCCESS.getCode());
        } catch (ExcelPluginException e) {
            // 花费毫秒数
            long timerCount = timer.interval();
            // 提示信息
            msgInfo = StrUtil.format(CoreMsg.EXCEL_EXPORT_ERROR.getMessage(), DateUtil.formatBetween(timerCount), e.getMessage());
            // 导出失败
            resultVo = ResultVo.error(CoreMsg.EXCEL_EXPORT_ERROR.getCode(), msgInfo);
        }finally {
            // 清空list
            modelList.clear();
        }
        // 记录导出日志
        log.info(msgInfo);

        // 导出异常
        if(!resultVo.isSuccess()){
            // 无权访问该方法
            OutputStreamUtil.exceptionResponse(resultVo.getMsg(), response);
        }
    }

    /**
     * 演示模式
     */
    protected void demoError(){
        UserModel user = UserUtil.getUser();
        // 演示模式 不允许操作 （超级管理员可以操作）
        if(globalProperties.isEnableDemo() &&
                !StringUtils.equals(UserUtil.SUPER_ADMIN, user.getUsername())){
            throw new ServiceException(CoreMsg.EXCEPTION_ENABLE_DEMO);
        }
    }


    /**
     * 处理是否包含子集
     * @param treeNodes 树节点
     */
    protected List<Tree<Object>> handleTreeHasChildren(List<Tree<Object>> treeNodes,
                                                       Function<Set<String>, List<HasChildren>> callback) {
        if(CollUtil.isEmpty(treeNodes) || callback == null){
            return treeNodes;
        }

        Set<String> parentIds = Sets.newHashSet();
        for (Tree<Object> treeNode : treeNodes) {
            parentIds.add(Convert.toStr(treeNode.getId()));
        }

        // 数据排查是否存在下级
        List<HasChildren> hasChildrenList = callback.apply(parentIds);
        if(CollUtil.isEmpty(hasChildrenList)){
            hasChildrenList = ListUtil.empty();
        }

        // 字典
        Map<String, Boolean> hasChildrenDict = Maps.newHashMap();
        for (HasChildren hasChildren : hasChildrenList) {
            if (hasChildren.getCount() != null && hasChildren.getCount() > 0) {
                hasChildrenDict.put(hasChildren.getParentId(), true);
            }
        }

        // 处理节点
        this.handleTreeHasChildren(treeNodes, hasChildrenDict);

        return treeNodes;
    }

    /**
     * 处理 树节点是否 有子节点
     * @param treeNodes 树节点
     * @param hasChildrenDict 字典树
     */
    private void handleTreeHasChildren(List<Tree<Object>> treeNodes,
                                       Map<String, Boolean> hasChildrenDict){

        for (Tree<Object> treeNode : treeNodes) {
            Boolean tmpFlag = hasChildrenDict.get(Convert.toStr(treeNode.getId()));
            if (tmpFlag != null && tmpFlag) {
                treeNode.putExtra(TreeConstants.IS_LEAF, false);
                treeNode.putExtra(TreeConstants.HAS_CHILDREN, true);
            }else {
                treeNode.putExtra(TreeConstants.IS_LEAF, true);
                treeNode.putExtra(TreeConstants.HAS_CHILDREN, false);
            }

            // 如果不为空 则继续递归处理
            if(CollUtil.isNotEmpty(treeNode.getChildren())){
                handleTreeHasChildren(treeNode.getChildren(), hasChildrenDict);
            }
        }
    }

    // =================================================

    @PostConstruct
    public void init(){
        try {
            this.modelClazz = IService.getModelClazz();
            this.entityClazz = IService.getEntityClazz();
            if(IService != null){
                // 判断Service 是否包含 热数据注解
                this.hotDataFlag = AnnotationUtil.hasAnnotation(IService.getServiceClazz(),
                        EnableHotData.class);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }


    /**
     * 创建包装类泛型对象
     * @return E
     */
    private E createModel() {
        try {
            Class<E> modelClazz = this.modelClazz;
            return modelClazz.newInstance();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

}
