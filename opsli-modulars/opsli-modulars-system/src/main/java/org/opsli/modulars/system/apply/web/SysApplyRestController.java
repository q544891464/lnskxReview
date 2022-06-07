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
package org.opsli.modulars.system.apply.web;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.opsli.common.annotation.RequiresPermissionsCus;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.opsli.api.base.result.ResultVo;
import org.opsli.common.annotation.ApiRestController;
import org.opsli.common.annotation.EnableLog;
import org.opsli.common.constants.MyBatisConstants;
import org.opsli.common.utils.FieldUtil;
import org.opsli.core.base.controller.BaseRestController;
import org.opsli.core.persistence.Page;
import org.opsli.core.persistence.querybuilder.QueryBuilder;
import org.opsli.core.persistence.querybuilder.WebQueryBuilder;
import org.opsli.core.utils.UserTokenUtil;
import org.opsli.core.utils.UserUtil;
import org.opsli.modulars.system.user.service.IUserOrgRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import org.opsli.modulars.system.apply.entity.SysApply;
import org.opsli.api.wrapper.system.apply.SysApplyModel;
import org.opsli.modulars.system.apply.service.ISysApplyService;
import org.opsli.api.web.system.apply.SysApplyRestApi;

/**
 * 申请表 Controller
 *
 * @author chinm
 * @date 2022-04-22 14:54:11
 */
@Api(tags = SysApplyRestApi.TITLE)
@Slf4j
@ApiRestController("/{ver}/system/apply")
public class SysApplyRestController extends BaseRestController<SysApply, SysApplyModel, ISysApplyService>
    implements SysApplyRestApi {

    @Autowired
    IUserOrgRefService userOrgRefService;

//    private final String CurrentUserId = UserTokenUtil.getUserIdByToken();


    /**
    * 申请表 查一条
    * @param model 模型
    * @return ResultVo
    */
    @ApiOperation(value = "获得单条申请表", notes = "获得单条申请表 - ID")
    @RequiresPermissions("system_apply_select")
    @Override
    public ResultVo<SysApplyModel> get(SysApplyModel model) {
        // 如果系统内部调用 则直接查数据库
        if(model != null && model.getIzApi() != null && model.getIzApi()){
            model = IService.get(model);
        }
        return ResultVo.success(model);
    }

    /**
    * 申请表 查询分页
    * @param pageNo 当前页
    * @param pageSize 每页条数
    * @param request request
    * @return ResultVo
    */
    @ApiOperation(value = "获得分页数据", notes = "获得分页数据 - 查询构造器")
    @RequiresPermissions("system_apply_select")
    @Override
    public ResultVo<?> findPage(Integer pageNo, Integer pageSize, HttpServletRequest request) {
        String CurrentUserId = UserTokenUtil.getUserIdByToken();

        QueryBuilder<SysApply> queryBuilder = new WebQueryBuilder<>(entityClazz, request.getParameterMap());
        Page<SysApply, SysApplyModel> page = new Page<>(pageNo, pageSize);
        QueryWrapper<SysApply> queryWrapper = queryBuilder.build();
//        只返回当前用户为申请者的申请表
        queryWrapper.eq("applicant_id",CurrentUserId);
        page.setQueryWrapper(queryWrapper);
        page = IService.findPage(page);

        return ResultVo.success(page.getPageData());
    }

    /**
     * 申请表 根据所在组织查询分页 一般只能组织特定角色才能查看
     * @param pageNo 当前页
     * @param pageSize 每页条数
     * @param request request
     * @return ResultVo
     */
    @ApiOperation(value = "获得分页数据", notes = "获得分页数据 - 查询构造器")
    @RequiresPermissions("system_apply_select")
    @Override
    public ResultVo<?> findPageByOrg(Integer pageNo, Integer pageSize, HttpServletRequest request) {
        //获取当前用户ID
        String CurrentUserId = UserTokenUtil.getUserIdByToken();
        //根据用户ID获取默认组织ID
        String orgId = userOrgRefService.getDefOrgId(CurrentUserId);

        //获取该用户所属组织的申请表列表
        QueryBuilder<SysApply> queryBuilder = new WebQueryBuilder<>(entityClazz, request.getParameterMap());
        Page<SysApply, SysApplyModel> page = new Page<>(pageNo, pageSize);
        QueryWrapper<SysApply> queryWrapper = queryBuilder.build();
        queryWrapper.eq("org_id",orgId);
        page.setQueryWrapper(queryWrapper);
        page = IService.findPage(page);

        return ResultVo.success(page.getPageData());
    }

    /**
    * 申请表 新增
    * @param model 模型
    * @return ResultVo
    */
    @ApiOperation(value = "新增申请表数据", notes = "新增申请表数据")
    @RequiresPermissions("system_apply_insert")
    @EnableLog
    @Override
    public ResultVo<?> insert(SysApplyModel model) {
        String CurrentUserId = UserTokenUtil.getUserIdByToken();

        //设置申请人ID
        model.setApplicantId(CurrentUserId);

        //设置申请人组织ID
        String orgId = userOrgRefService.getDefOrgId(CurrentUserId);

        model.setOrgId(orgId);
        // 调用新增方法
        IService.insert(model);
        return ResultVo.success("新增申请表成功");
    }

    /**
    * 申请表 修改
    * @param model 模型
    * @return ResultVo
    */
    @ApiOperation(value = "修改申请表数据", notes = "修改申请表数据")
    @RequiresPermissions("system_apply_update")
    @EnableLog
    @Override
    public ResultVo<?> update(SysApplyModel model) {
        // 调用修改方法
        IService.update(model);
        return ResultVo.success("修改申请表成功");
    }


    /**
    * 申请表 删除
    * @param id ID
    * @return ResultVo
    */
    @ApiOperation(value = "删除申请表数据", notes = "删除申请表数据")
    @RequiresPermissions("system_apply_update")
    @EnableLog
    @Override
    public ResultVo<?> del(String id){
        IService.delete(id);
        return ResultVo.success("删除申请表成功");
    }

    /**
     * 申请表 改变通过状态
     * @return ResultVo
     */
    @ApiOperation(value = "改变通过状态", notes = "改变通过状态")
    @RequiresPermissions("system_apply_update")
    @Override
    public ResultVo<?> isPassApply(String applyId,String isPass) {

        boolean isPassStatus = IService.IsPassApply(applyId,isPass);
        if(!isPassStatus){
            return ResultVo.error("变更通过状态失败");
        }
        return ResultVo.success();
    }

    /**
    * 申请表 批量删除
    * @param ids ID 数组
    * @return ResultVo
    */
    @ApiOperation(value = "批量删除申请表数据", notes = "批量删除申请表数据")
    @RequiresPermissions("system_apply_update")
    @EnableLog
    @Override
    public ResultVo<?> delAll(String ids){
        String[] idArray = Convert.toStrArray(ids);
        IService.deleteAll(idArray);
        return ResultVo.success("批量删除申请表成功");
    }


    /**
    * 申请表 Excel 导出
    * 注：这里 RequiresPermissionsCus 引入的是 自定义鉴权注解
    *
    * 导出时，Token认证和方法权限认证 全部都由自定义完成
    * 因为在 导出不成功时，需要推送错误信息，
    * 前端直接走下载流，当失败时无法获得失败信息，即使前后端换一种方式后端推送二进制文件前端再次解析也是最少2倍的耗时
    * ，且如果数据量过大，前端进行渲染时直接会把浏览器卡死
    * 而直接开启socket接口推送显然是太过浪费资源了，所以目前采用Java最原始的手段
    * response 推送 javascript代码 alert 提示报错信息
    *
    * @param request request
    * @param response response
    */
    @ApiOperation(value = "导出Excel", notes = "导出Excel")
    @RequiresPermissionsCus("system_apply_export")
    @EnableLog
    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        // 当前方法
        Method method = ReflectUtil.getMethodByName(this.getClass(), "exportExcel");
        QueryBuilder<SysApply> queryBuilder = new WebQueryBuilder<>(entityClazz, request.getParameterMap());
        super.excelExport(SysApplyRestApi.SUB_TITLE, queryBuilder.build(), response, method);
    }

    /**
    * 申请表 Excel 导入
    * 注：这里 RequiresPermissions 引入的是 Shiro原生鉴权注解
    * @param request 文件流 request
    * @return ResultVo
    */
    @ApiOperation(value = "导入Excel", notes = "导入Excel")
    @RequiresPermissions("system_apply_import")
    @EnableLog
    @Override
    public ResultVo<?> importExcel(MultipartHttpServletRequest request) {
        return super.importExcel(request);
    }

    /**
    * 申请表 Excel 下载导入模版
    * 注：这里 RequiresPermissionsCus 引入的是 自定义鉴权注解
    * @param response response
    */
    @ApiOperation(value = "导出Excel模版", notes = "导出Excel模版")
    @RequiresPermissionsCus("system_apply_import")
    @Override
    public void importTemplate(HttpServletResponse response) {
        // 当前方法
        Method method = ReflectUtil.getMethodByName(this.getClass(), "importTemplate");
        super.importTemplate(SysApplyRestApi.SUB_TITLE, response, method);
    }

}