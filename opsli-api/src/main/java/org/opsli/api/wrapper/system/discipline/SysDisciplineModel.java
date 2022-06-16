package org.opsli.api.wrapper.system.discipline;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.api.base.warpper.ApiWrapper;
import org.opsli.common.annotation.validator.Validator;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.common.enums.ValidatorType;
import org.opsli.plugins.excel.annotation.ExcelInfo;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysDisciplineModel extends ApiWrapper {

    /** 父级主键 */
    @ApiModelProperty(value = "父级主键")
    @ExcelProperty(value = "父级主键", order = 1)
    @ExcelInfo
    @ValidatorLenMax(19)
    private String parentId;

    /** 专业编号 */
    @ApiModelProperty(value = "专业编号")
    @ExcelProperty(value = "专业编号", order = 2)
    @ExcelInfo
    @Validator({ValidatorType.IS_NOT_NULL, ValidatorType.IS_INTEGER})
    @ValidatorLenMax(40)
    private String disciplineCode;

    /** 专业名称 */
    @ApiModelProperty(value = "专业名称")
    @ExcelProperty(value = "专业名称", order = 3)
    @ExcelInfo
    @Validator({ValidatorType.IS_NOT_NULL})
    @ValidatorLenMax(40)
    private String disciplineName;
}
