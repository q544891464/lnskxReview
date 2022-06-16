package org.opsli.modulars.system.discipline.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.opsli.core.base.entity.HasChildren;
import org.opsli.modulars.system.area.entity.SysArea;
import org.opsli.modulars.system.discipline.entity.SysDiscipline;

import java.util.List;

@Mapper
public interface SysDisciplineMapper extends BaseMapper<SysDiscipline> {

    /**
     * 是否有下级
     * @param wrapper 查询器
     * @return List
     */
    List<HasChildren> hasChildren(@Param(Constants.WRAPPER) Wrapper<?> wrapper);
}
