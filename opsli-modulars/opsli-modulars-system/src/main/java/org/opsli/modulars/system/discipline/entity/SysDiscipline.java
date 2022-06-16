package org.opsli.modulars.system.discipline.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.core.base.entity.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysDiscipline extends BaseEntity {

    /** 父级主键 */
    private String parentId;

    /** 专业编号 */
    private String disciplineCode;

    /** 专业名称 */
    private String disciplineName;


    // ========================================


    /** 逻辑删除字段 */
    @TableLogic
    private String deleted;

}
