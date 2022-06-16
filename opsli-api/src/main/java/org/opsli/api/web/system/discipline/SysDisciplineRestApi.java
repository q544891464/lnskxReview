package org.opsli.api.web.system.discipline;

import org.opsli.api.base.result.ResultVo;
import org.opsli.api.wrapper.system.discipline.SysDisciplineModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SysDisciplineRestApi {
    /** 标题 */
    String TITLE = "专业管理";
    /** 子标题 */
    String SUB_TITLE = "专业";

    /**
     * 组织机构表 查一条
     * @param model 模型
     * @return ResultVo
     */
    @GetMapping("/get")
    ResultVo<SysDisciplineModel> get(SysDisciplineModel model);

    /**
     * 组织树
     * @param parentId 父节点ID
     * @return ResultVo
     */
    @GetMapping("/findTree")
    ResultVo<?> findTree(String parentId);

    /**
     * 组织树
     * @param deep 层级
     * @return ResultVo
     */
    @GetMapping("/findTreeAll")
    ResultVo<?> findTreeAll(@RequestParam(name = "deep", defaultValue = "3", required = false) Integer deep);

    /**
     * 组织机构表 新增
     * @param model 模型
     * @return ResultVo
     */
    @PostMapping("/insert")
    ResultVo<?> insert(@RequestBody SysDisciplineModel model);

    /**
     * 组织机构表 修改
     * @param model 模型
     * @return ResultVo
     */
    @PostMapping("/update")
    ResultVo<?> update(@RequestBody SysDisciplineModel model);

    /**
     * 组织机构表 删除
     * @param id ID
     * @return ResultVo
     */
    @PostMapping("/del")
    ResultVo<?> del(String id);

    /**
     * 组织机构表 批量删除
     * @param ids ID 数组
     * @return ResultVo
     */
    @PostMapping("/delAll")
    ResultVo<?> delAll(String ids);

    /**
     * 组织机构表 Excel 导出
     * @param request request
     * @param response response
     */
    @GetMapping("/exportExcel")
    void exportExcel(HttpServletRequest request, HttpServletResponse response);

    /**
     * 组织机构表 Excel 导入
     * @param request 文件流 request
     * @return ResultVo
     */
    @PostMapping("/importExcel")
    ResultVo<?> importExcel(MultipartHttpServletRequest request);

    /**
     * 组织机构表 Excel 下载导入模版
     * @param response response
     */
    @GetMapping("/importExcel/template")
    void importTemplate(HttpServletResponse response);
}
