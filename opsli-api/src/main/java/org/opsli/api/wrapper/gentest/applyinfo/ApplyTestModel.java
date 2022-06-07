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
package org.opsli.api.wrapper.gentest.applyinfo;

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
* 申请表信息 Model
*
* @author chinm
* @date 2022-04-21 15:59:11
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class ApplyTestModel extends ApiWrapper {

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称")
    @ExcelProperty(value = "项目名称", order = 1)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String projectName;

    /** 申请者名称 */
    @ApiModelProperty(value = "申请者名称")
    @ExcelProperty(value = "申请者名称", order = 2)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String applicantName;

    /** 是否启用 */
    @ApiModelProperty(value = "是否启用")
    @ExcelProperty(value = "是否启用", order = 3)
    @ExcelInfo
    @ValidatorLenMax(1)
    private String izUsable;



}