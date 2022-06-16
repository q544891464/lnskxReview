package org.opsli.modulars.system.discipline.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.opsli.api.base.result.ResultVo;

import org.opsli.api.web.system.discipline.SysDisciplineRestApi;

import org.opsli.api.wrapper.system.discipline.SysDisciplineModel;
import org.opsli.common.annotation.ApiRestController;
import org.opsli.common.annotation.EnableLog;
import org.opsli.common.annotation.RequiresPermissionsCus;
import org.opsli.common.constants.MyBatisConstants;
import org.opsli.common.utils.FieldUtil;
import org.opsli.core.base.controller.BaseRestController;
import org.opsli.core.persistence.querybuilder.QueryBuilder;
import org.opsli.core.persistence.querybuilder.WebQueryBuilder;
import org.opsli.core.utils.TreeBuildUtil;
import org.opsli.modulars.system.discipline.entity.SysDiscipline;
import org.opsli.modulars.system.discipline.service.ISysDisciplineService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Api(tags = SysDisciplineRestApi.TITLE)
@Slf4j
@ApiRestController("/{ver}/system/discipline")
public class SysDisciplineRestController extends BaseRestController<SysDiscipline, SysDisciplineModel, ISysDisciplineService>
    implements SysDisciplineRestApi {

    /** 排序字段 */
    private static final String SORT_FIELD = "sortNo";

    /**
     * 地域 查一条
     * @param model 模型
     * @return ResultVo
     */
    @ApiOperation(value = "获得单条地域", notes = "获得单条地域 - ID")
    @RequiresPermissions("system_discipline_select")
    @Override
    public ResultVo<SysDisciplineModel> get(SysDisciplineModel model) {
        // 如果系统内部调用 则直接查数据库
        if(model != null && model.getIzApi() != null && model.getIzApi()){
            model = IService.get(model);
        }
        return ResultVo.success(model);
    }


    /**
     * 获得组织树树
     * @return ResultVo
     */
    @ApiOperation(value = "获得菜单树", notes = "获得菜单树")
    @RequiresPermissions("system_discipline_select")
    @Override
    public ResultVo<?> findTree(String parentId) {

        QueryWrapper<SysDiscipline> wrapper = new QueryWrapper<>();
        wrapper.eq(FieldUtil.humpToUnderline(MyBatisConstants.FIELD_PARENT_ID), parentId);
        List<SysDiscipline> dataList =  IService.findList(wrapper);

        // 获得BeanMapList
        List<Map<String, Object>> beanMapList = this.getBeanMapList(dataList);

        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey(SORT_FIELD);
        // 最大递归深度 最多支持1层
        treeNodeConfig.setDeep(1);

        //转换器
        List<Tree<Object>> treeNodes = TreeBuildUtil.INSTANCE.build(beanMapList, parentId, treeNodeConfig);

        // 处理是否包含子集
        super.handleTreeHasChildren(treeNodes,
                (parentIds)-> IService.hasChildren(parentIds));

        return ResultVo.success(treeNodes);
    }

    /**
     * 获取全量地域列表
     *
     * @param deep 层级
     *
     *
     * @return ResultVo
     */
    @ApiOperation(value = "获取全量地域列表", notes = "获取全量地域列表")
//    @RequiresPermissions("system_discipline_select")
    @Override
    public ResultVo<?> findTreeAll(Integer deep) {

        List<SysDiscipline> dataList =  IService.findList(new QueryWrapper<>());

        // 获得BeanMapList
        List<Map<String, Object>> beanMapList = this.getBeanMapList(dataList);

        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey(SORT_FIELD);
        // 最大递归深度 最多支持1层
        treeNodeConfig.setDeep(deep);

        //转换器
        List<Tree<Object>> treeNodes = TreeBuildUtil.INSTANCE.build(beanMapList, treeNodeConfig);

        // 处理是否包含子集
        super.handleTreeHasChildren(treeNodes,
                (parentIds)-> IService.hasChildren(parentIds));

        return ResultVo.success(treeNodes);
    }

    /**
     * 地域 新增
     * @param model 模型
     * @return ResultVo
     */
    @ApiOperation(value = "新增地域数据", notes = "新增地域数据")
    @RequiresPermissions("system_discipline_insert")
    @EnableLog
    @Override
    public ResultVo<?> insert(SysDisciplineModel model) {
        // 演示模式 不允许操作
        super.demoError();

        // 调用新增方法
        IService.insert(model);
        return ResultVo.success("新增地域成功");
    }

    /**
     * 地域 修改
     * @param model 模型
     * @return ResultVo
     */
    @ApiOperation(value = "修改地域数据", notes = "修改地域数据")
    @RequiresPermissions("system_discipline_update")
    @EnableLog
    @Override
    public ResultVo<?> update(SysDisciplineModel model) {
        // 演示模式 不允许操作
        super.demoError();

        // 调用修改方法
        IService.update(model);
        return ResultVo.success("修改地域成功");
    }


    /**
     * 地域 删除
     * @param id ID
     * @return ResultVo
     */
    @ApiOperation(value = "删除地域数据", notes = "删除地域数据")
    @RequiresPermissions("system_discipline_update")
    @EnableLog
    @Override
    public ResultVo<?> del(String id){
        // 演示模式 不允许操作
        super.demoError();

        IService.delete(id);
        return ResultVo.success("删除地域成功");
    }

    /**
     * 地域 批量删除
     * @param ids ID 数组
     * @return ResultVo
     */
    @ApiOperation(value = "批量删除地域数据", notes = "批量删除地域数据")
    @RequiresPermissions("system_discipline_update")
    @EnableLog
    @Override
    public ResultVo<?> delAll(String ids){
        // 演示模式 不允许操作
        super.demoError();

        String[] idArray = Convert.toStrArray(ids);
        IService.deleteAll(idArray);

        return ResultVo.success("批量删除地域成功");
    }


    /**
     * 地域 Excel 导出
     * @param request request
     * @param response response
     */
    @ApiOperation(value = "导出Excel", notes = "导出Excel")
    @RequiresPermissionsCus("system_discipline_export")
    @EnableLog
    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        // 当前方法
        Method method = ReflectUtil.getMethodByName(this.getClass(), "exportExcel");
        QueryBuilder<SysDiscipline> queryBuilder = new WebQueryBuilder<>(entityClazz, request.getParameterMap());
        super.excelExport(SysDisciplineRestApi.SUB_TITLE, queryBuilder.build(), response, method);
    }

    /**
     * 地域 Excel 导入
     * @param request 文件流 request
     * @return ResultVo
     */
    @ApiOperation(value = "导入Excel", notes = "导入Excel")
    @RequiresPermissions("system_discipline_import")
    @EnableLog
    @Override
    public ResultVo<?> importExcel(MultipartHttpServletRequest request) {
        return super.importExcel(request);
    }

    /**
     * 地域 Excel 下载导入模版
     * @param response response
     */
    @ApiOperation(value = "导出Excel模版", notes = "导出Excel模版")
    @RequiresPermissionsCus("system_discipline_import")
    @Override
    public void importTemplate(HttpServletResponse response) {
        // 当前方法
        Method method = ReflectUtil.getMethodByName(this.getClass(), "importTemplate");
        super.importTemplate(SysDisciplineRestApi.SUB_TITLE, response, method);
    }

    // ==============================

    /**
     * 获得BeanMap集合
     * @param dataList 数据集合
     * @return List
     */
    private List<Map<String, Object>> getBeanMapList(List<SysDiscipline> dataList) {
        List<Map<String, Object>> beanMapList = Lists.newArrayList();
        if(CollUtil.isEmpty(dataList)){
            return beanMapList;
        }

        // 转化为 BeanMap 处理数据
        for (SysDiscipline sysDiscipline : dataList) {
            Map<String, Object> beanToMap = BeanUtil.beanToMap(sysDiscipline);

            // 获得排序
            String disciplineCode = sysDiscipline.getDisciplineCode();
            int sort = 0;
            if(StringUtils.isNotEmpty(disciplineCode)){
                try {
                    sort = Integer.parseInt(disciplineCode);
                }catch (Exception ignored){}
            }
            beanToMap.put(SORT_FIELD, sort);

            beanMapList.add(beanToMap);
        }
        return beanMapList;
    }


    /**
     * 导入数据
     */
    //@ApiOperation(value = "获得json数据 查询数据", notes = "获得json数据 查询数据")
    //@GetMapping("/importJson")
    public void importJson() {
        // https://github.com/small-dream/China_Province_City
        // JSON 放在 resources下更新当前数据库数据
        ClassPathResource resource = new ClassPathResource("/json/2020年8月中华人民共和国县以上行政区划代码.json");
        try (InputStream inputStream = resource.getInputStream()) {
            String readTpl = IoUtil.read(inputStream, StandardCharsets.UTF_8);

            List<SysDisciplineModel> tmpList = Lists.newArrayList();

            // 填充省份
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);

            String baseId = snowflake.nextIdStr();
            SysDisciplineModel model = new SysDisciplineModel();
            model.setId(baseId);
            model.setDisciplineCode("86");
            model.setDisciplineName("中国");
            model.setParentId("0");
            tmpList.add(model);

            JSONArray jsonArray = JSONArray.parseArray(readTpl);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // 填充省份
                SysDisciplineModel sysDisciplineModel = new SysDisciplineModel();
                String sId = snowflake.nextIdStr();
                sysDisciplineModel.setId(sId);
                sysDisciplineModel.setDisciplineCode((String) jsonObject.get("code"));
                sysDisciplineModel.setDisciplineName((String) jsonObject.get("name"));
                sysDisciplineModel.setParentId(baseId);
                tmpList.add(sysDisciplineModel);

                JSONArray jsonArray2 = jsonObject.getJSONArray("cityList");
                for (int j = 0; j < jsonArray2.size(); j++) {
                    JSONObject jsonObject2 = jsonArray2.getJSONObject(j);
                    // 填充城市
                    SysDisciplineModel sysDisciplineModel2 = new SysDisciplineModel();
                    String cityId = snowflake.nextIdStr();
                    sysDisciplineModel2.setId(cityId);
                    sysDisciplineModel2.setDisciplineCode((String) jsonObject2.get("code"));
                    sysDisciplineModel2.setDisciplineName((String) jsonObject2.get("name"));
                    sysDisciplineModel2.setParentId(sId);
                    tmpList.add(sysDisciplineModel2);


                    JSONArray jsonArray3 = jsonObject2.getJSONArray("disciplineList");
                    if(jsonArray3 != null){
                        for (int k = 0; k < jsonArray3.size(); k++) {
                            JSONObject jsonObject3 = jsonArray3.getJSONObject(k);
                            // 填充城市
                            SysDisciplineModel sysDisciplineModel3 = new SysDisciplineModel();
                            String disciplineId = snowflake.nextIdStr();
                            sysDisciplineModel3.setId(disciplineId);
                            sysDisciplineModel3.setDisciplineCode((String) jsonObject3.get("code"));
                            sysDisciplineModel3.setDisciplineName((String) jsonObject3.get("name"));
                            sysDisciplineModel3.setParentId(cityId);
                            tmpList.add(sysDisciplineModel3);
                        }
                    }

                }

            }

            IService.insertBatch(tmpList);

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
