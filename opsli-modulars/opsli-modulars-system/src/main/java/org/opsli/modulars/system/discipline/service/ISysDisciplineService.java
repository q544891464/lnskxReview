package org.opsli.modulars.system.discipline.service;

import org.opsli.api.wrapper.system.area.SysAreaModel;
import org.opsli.api.wrapper.system.discipline.SysDisciplineModel;
import org.opsli.core.base.entity.HasChildren;
import org.opsli.core.base.service.interfaces.CrudServiceInterface;
import org.opsli.modulars.system.area.entity.SysArea;
import org.opsli.modulars.system.discipline.entity.SysDiscipline;

import java.util.List;
import java.util.Set;

public interface ISysDisciplineService extends CrudServiceInterface<SysDiscipline, SysDisciplineModel> {


    /**
     * 是否有下级
     * @param parentIds 父级节点集合
     * @return List
     */
    List<HasChildren> hasChildren(Set<String> parentIds);
}
