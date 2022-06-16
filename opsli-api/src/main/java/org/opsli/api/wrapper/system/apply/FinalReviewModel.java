package org.opsli.api.wrapper.system.apply;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.api.base.warpper.ApiWrapper;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.plugins.excel.annotation.ExcelInfo;


/**
 * 申请表 Model
 *
 * @author chinm
 * @date 2022-04-27 09:18:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FinalReviewModel extends ApiWrapper {

    /** 申请项目名称 */
    @ApiModelProperty(value = "申请项目名称")
    @ExcelProperty(value = "申请项目名称", order = 5)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String applyName;

    /** 申请用户id */
    @ApiModelProperty(value = "申请用户id")
    @ExcelProperty(value = "申请用户id", order = 6)
    @ExcelIgnore
    @ValidatorLenMax(19)
    private String applicantId;

    /** 组织id */
    @ApiModelProperty(value = "组织id")
    @ExcelProperty(value = "组织id", order = 6)
    @ExcelIgnore
    @ValidatorLenMax(19)
    private String orgId;

    /** 是否通过:0未通过，1通过 */
    @ApiModelProperty(value = "是否通过:0未通过，1通过")
    @ExcelProperty(value = "是否通过:0未通过，1通过", order = 11)
    @ExcelIgnore
    @ValidatorLenMax(1)
    private String isPass;

    /** 主要完成人 */
    @ApiModelProperty(value = "主要完成人")
    @ExcelProperty(value = "主要完成人", order = 12)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String mainAuthors;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    @ExcelProperty(value = "关键词", order = 13)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String keywords;

    /** 任务来源 */
    @ApiModelProperty(value = "任务来源")
    @ExcelProperty(value = "任务来源", order = 14)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String taskSource;

    /** 具体计划、基金名称和编号 */
    @ApiModelProperty(value = "具体计划、基金名称和编号")
    @ExcelProperty(value = "具体计划、基金名称和编号", order = 15)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String fundName;

    /** 成果简介 */
    @ApiModelProperty(value = "成果简介")
    @ExcelProperty(value = "成果简介", order = 16)
    @ExcelInfo
    private String introduction;

    /** 成果原创性、前沿性、突破性创新内容和学术贡献 */
    @ApiModelProperty(value = "成果原创性、前沿性、突破性创新内容和学术贡献")
    @ExcelProperty(value = "成果原创性、前沿性、突破性创新内容和学术贡献", order = 17)
    @ExcelInfo
    private String innovation;

    /** 成果应用情况 */
    @ApiModelProperty(value = "成果应用情况")
    @ExcelProperty(value = "成果应用情况", order = 18)
    @ExcelInfo
    private String application;

}
