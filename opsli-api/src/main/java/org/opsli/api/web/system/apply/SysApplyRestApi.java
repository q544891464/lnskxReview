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
package org.opsli.api.web.system.apply;

import org.opsli.api.base.result.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opsli.api.wrapper.system.apply.SysApplyModel;


/**
 * 申请表 Api
 *
 * 对外 API 直接 暴露 @GetMapping 或者 @PostMapping
 * 对内也推荐 单机版 不需要设置 Mapping 但是调用方法得从Controller写起
 *
 * 这样写法虽然比较绕，但是当单体项目想要改造微服务架构时 时非常容易的
 *
 * @author chinm
 * @date 2022-04-25 15:31:43
 */
public interface SysApplyRestApi {

    /** 标题 */
    String TITLE = "申请表";
    /** 子标题 */
    String SUB_TITLE = "申请表";

    /**
    * 申请表 查一条
    * @param model 模型
    * @return ResultVo
    */
    @GetMapping("/get")
    ResultVo<SysApplyModel> get(SysApplyModel model);

    /**
    * 申请表 查询分页
    * @param pageNo 当前页
    * @param pageSize 每页条数
    * @param request request
    * @return ResultVo
    */
    @GetMapping("/findPage")
    ResultVo<?> findPage(
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest request
    );

    /**
     * 申请表 查询分页
     * @param pageNo 当前页
     * @param pageSize 每页条数
     * @param request request
     * @return ResultVo
     */
    @GetMapping("/findPageByOrg")
    ResultVo<?> findPageByOrg(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest request
    );


    /**
     * 申请表 查询分页 通过is_pass状态获得分页
     * @param pageNo 当前页
     * @param pageSize 每页条数
     * @param request request
     * @return ResultVo
     */
    @GetMapping("/findPageByIsPassed")
    ResultVo<?> findPageByIsPassed(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest request
    );

    /**
    * 申请表 新增
    * @param model 模型
    * @return ResultVo
    */
    @PostMapping("/insert")
    ResultVo<?> insert(@RequestBody SysApplyModel model);

    /**
    * 申请表 修改
    * @param model 模型
    * @return ResultVo
    */
    @PostMapping("/update")
    ResultVo<?> update(@RequestBody SysApplyModel model);

    /**
    * 申请表 删除
    * @param id ID
    * @return ResultVo
    */
    @PostMapping("/del")
    ResultVo<?> del(String id);

    /**
    * 申请表 批量删除
    * @param ids ID 数组
    * @return ResultVo
    */
    @PostMapping("/delAll")
    ResultVo<?> delAll(String ids);

    /**
     * 申请表 通过状态改变
     * @param applyId 租户ID
     * @param isPass 状态
     * @return ResultVo
     */
    @PostMapping("/isPassApply")
    ResultVo<?> isPassApply(String applyId, String isPass);

    /**
     * 申请表 设置奖项
     * @param applyId 申请ID
     * @param prize 奖项
     * @return ResultVo
     */
    @PostMapping("/setPrize")
    ResultVo<?> setPrize(String applyId, String prize);


    /**
     * 申请表 上传pdf文件

     * @return ResultVo
     */
    @PostMapping("/uploadFile")
    ResultVo<?> uploadFile(MultipartHttpServletRequest request);

    /**
     * 申请表 上传其他证明文件

     * @return ResultVo
     */
    @PostMapping("/uploadOtherFile")
    ResultVo<?> uploadOtherFile(MultipartHttpServletRequest request);

    /**
     * 申请表 上传签字盖章后的文件

     * @return ResultVo
     */
    @PostMapping("/uploadCompletedFile")
    ResultVo<?> uploadCompletedFile(MultipartHttpServletRequest request);

    /**
    * 申请表 Excel 导出
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
    @GetMapping("/exportExcel")
    void exportExcel(HttpServletRequest request, HttpServletResponse response);

    @GetMapping("/exportExcelById")
    void exportExcelById(HttpServletRequest request, HttpServletResponse response);

    @GetMapping("/exportExcelByOrg")
    void exportExcelByOrg(HttpServletRequest request, HttpServletResponse response);

    @GetMapping("/exportExcelByIsPass")
    void exportExcelByIsPass(HttpServletRequest request, HttpServletResponse response);

    /**
    * 申请表 Excel 导入
    * @param request 文件流 request
    * @return ResultVo
    */
    @PostMapping("/importExcel")
    ResultVo<?> importExcel(MultipartHttpServletRequest request);

    /**
    * 申请表 Excel 下载导入模版
    * @param response response
    */
    @GetMapping("/importExcel/template")
    void importTemplate(HttpServletResponse response);

}