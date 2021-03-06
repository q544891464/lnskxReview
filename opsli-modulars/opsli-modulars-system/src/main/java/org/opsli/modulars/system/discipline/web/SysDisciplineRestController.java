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

    /** ???????????? */
    private static final String SORT_FIELD = "sortNo";

    /**
     * ?????? ?????????
     * @param model ??????
     * @return ResultVo
     */
    @ApiOperation(value = "??????????????????", notes = "?????????????????? - ID")
    @RequiresPermissions("system_discipline_select")
    @Override
    public ResultVo<SysDisciplineModel> get(SysDisciplineModel model) {
        // ???????????????????????? ?????????????????????
        if(model != null && model.getIzApi() != null && model.getIzApi()){
            model = IService.get(model);
        }
        return ResultVo.success(model);
    }


    /**
     * ??????????????????
     * @return ResultVo
     */
    @ApiOperation(value = "???????????????", notes = "???????????????")
    @RequiresPermissions("system_discipline_select")
    @Override
    public ResultVo<?> findTree(String parentId) {

        QueryWrapper<SysDiscipline> wrapper = new QueryWrapper<>();
        wrapper.eq(FieldUtil.humpToUnderline(MyBatisConstants.FIELD_PARENT_ID), parentId);
        List<SysDiscipline> dataList =  IService.findList(wrapper);

        // ??????BeanMapList
        List<Map<String, Object>> beanMapList = this.getBeanMapList(dataList);

        //??????
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // ?????????????????? ??????????????????
        treeNodeConfig.setWeightKey(SORT_FIELD);
        // ?????????????????? ????????????1???
        treeNodeConfig.setDeep(1);

        //?????????
        List<Tree<Object>> treeNodes = TreeBuildUtil.INSTANCE.build(beanMapList, parentId, treeNodeConfig);

        // ????????????????????????
        super.handleTreeHasChildren(treeNodes,
                (parentIds)-> IService.hasChildren(parentIds));

        return ResultVo.success(treeNodes);
    }

    /**
     * ????????????????????????
     *
     * @param deep ??????
     *
     *
     * @return ResultVo
     */
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
//    @RequiresPermissions("system_discipline_select")
    @Override
    public ResultVo<?> findTreeAll(Integer deep) {

        List<SysDiscipline> dataList =  IService.findList(new QueryWrapper<>());

        // ??????BeanMapList
        List<Map<String, Object>> beanMapList = this.getBeanMapList(dataList);

        //??????
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // ?????????????????? ??????????????????
        treeNodeConfig.setWeightKey(SORT_FIELD);
        // ?????????????????? ????????????1???
        treeNodeConfig.setDeep(deep);

        //?????????
        List<Tree<Object>> treeNodes = TreeBuildUtil.INSTANCE.build(beanMapList, treeNodeConfig);

        // ????????????????????????
        super.handleTreeHasChildren(treeNodes,
                (parentIds)-> IService.hasChildren(parentIds));

        return ResultVo.success(treeNodes);
    }

    /**
     * ?????? ??????
     * @param model ??????
     * @return ResultVo
     */
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @RequiresPermissions("system_discipline_insert")
    @EnableLog
    @Override
    public ResultVo<?> insert(SysDisciplineModel model) {
        // ???????????? ???????????????
        super.demoError();

        // ??????????????????
        IService.insert(model);
        return ResultVo.success("??????????????????");
    }

    /**
     * ?????? ??????
     * @param model ??????
     * @return ResultVo
     */
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @RequiresPermissions("system_discipline_update")
    @EnableLog
    @Override
    public ResultVo<?> update(SysDisciplineModel model) {
        // ???????????? ???????????????
        super.demoError();

        // ??????????????????
        IService.update(model);
        return ResultVo.success("??????????????????");
    }


    /**
     * ?????? ??????
     * @param id ID
     * @return ResultVo
     */
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @RequiresPermissions("system_discipline_update")
    @EnableLog
    @Override
    public ResultVo<?> del(String id){
        // ???????????? ???????????????
        super.demoError();

        IService.delete(id);
        return ResultVo.success("??????????????????");
    }

    /**
     * ?????? ????????????
     * @param ids ID ??????
     * @return ResultVo
     */
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    @RequiresPermissions("system_discipline_update")
    @EnableLog
    @Override
    public ResultVo<?> delAll(String ids){
        // ???????????? ???????????????
        super.demoError();

        String[] idArray = Convert.toStrArray(ids);
        IService.deleteAll(idArray);

        return ResultVo.success("????????????????????????");
    }


    /**
     * ?????? Excel ??????
     * @param request request
     * @param response response
     */
    @ApiOperation(value = "??????Excel", notes = "??????Excel")
    @RequiresPermissionsCus("system_discipline_export")
    @EnableLog
    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        // ????????????
        Method method = ReflectUtil.getMethodByName(this.getClass(), "exportExcel");
        QueryBuilder<SysDiscipline> queryBuilder = new WebQueryBuilder<>(entityClazz, request.getParameterMap());
        super.excelExport(SysDisciplineRestApi.SUB_TITLE, queryBuilder.build(), response, method);
    }

    /**
     * ?????? Excel ??????
     * @param request ????????? request
     * @return ResultVo
     */
    @ApiOperation(value = "??????Excel", notes = "??????Excel")
    @RequiresPermissions("system_discipline_import")
    @EnableLog
    @Override
    public ResultVo<?> importExcel(MultipartHttpServletRequest request) {
        return super.importExcel(request);
    }

    /**
     * ?????? Excel ??????????????????
     * @param response response
     */
    @ApiOperation(value = "??????Excel??????", notes = "??????Excel??????")
    @RequiresPermissionsCus("system_discipline_import")
    @Override
    public void importTemplate(HttpServletResponse response) {
        // ????????????
        Method method = ReflectUtil.getMethodByName(this.getClass(), "importTemplate");
        super.importTemplate(SysDisciplineRestApi.SUB_TITLE, response, method);
    }

    // ==============================

    /**
     * ??????BeanMap??????
     * @param dataList ????????????
     * @return List
     */
    private List<Map<String, Object>> getBeanMapList(List<SysDiscipline> dataList) {
        List<Map<String, Object>> beanMapList = Lists.newArrayList();
        if(CollUtil.isEmpty(dataList)){
            return beanMapList;
        }

        // ????????? BeanMap ????????????
        for (SysDiscipline sysDiscipline : dataList) {
            Map<String, Object> beanToMap = BeanUtil.beanToMap(sysDiscipline);

            // ????????????
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
     * ????????????
     */
    //@ApiOperation(value = "??????json?????? ????????????", notes = "??????json?????? ????????????")
    //@GetMapping("/importJson")
    public void importJson() {
        // https://github.com/small-dream/China_Province_City
        // JSON ?????? resources??????????????????????????????
        ClassPathResource resource = new ClassPathResource("/json/2020???8???????????????????????????????????????????????????.json");
        try (InputStream inputStream = resource.getInputStream()) {
            String readTpl = IoUtil.read(inputStream, StandardCharsets.UTF_8);

            List<SysDisciplineModel> tmpList = Lists.newArrayList();

            // ????????????
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);

            String baseId = snowflake.nextIdStr();
            SysDisciplineModel model = new SysDisciplineModel();
            model.setId(baseId);
            model.setDisciplineCode("86");
            model.setDisciplineName("??????");
            model.setParentId("0");
            tmpList.add(model);

            JSONArray jsonArray = JSONArray.parseArray(readTpl);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // ????????????
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
                    // ????????????
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
                            // ????????????
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
