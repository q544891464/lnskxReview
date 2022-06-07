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
package org.opsli.api.wrapper.system.apply;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.api.base.warpper.ApiWrapper;
import org.opsli.common.annotation.validator.Validator;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.common.annotation.validator.ValidatorLenMin;
import org.opsli.common.enums.ValidatorType;
import org.opsli.plugins.excel.annotation.ExcelInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 申请表 Model
*
* @author chinm
* @date 2022-04-27 09:18:03
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysApplyModel extends ApiWrapper {

    /** 申请项目名称 */
    @ApiModelProperty(value = "申请项目名称")
    @ExcelProperty(value = "申请项目名称", order = 5)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String applyName;

    /** 申请用户id */
    @ApiModelProperty(value = "申请用户id")
    @ExcelProperty(value = "申请用户id", order = 6)
    @ExcelInfo
    @ValidatorLenMax(19)
    private String applicantId;

    /** 组织id */
    @ApiModelProperty(value = "组织id")
    @ExcelProperty(value = "组织id", order = 6)
    @ExcelInfo
    @ValidatorLenMax(19)
    private String orgId;

    /** 是否通过:0未通过，1通过 */
    @ApiModelProperty(value = "是否通过:0未通过，1通过")
    @ExcelProperty(value = "是否通过:0未通过，1通过", order = 11)
    @ExcelInfo
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