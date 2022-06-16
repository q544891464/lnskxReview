package org.opsli.modulars.system.discipline.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.opsli.api.wrapper.system.discipline.SysDisciplineModel;
import org.opsli.common.constants.MyBatisConstants;
import org.opsli.common.enums.DictType;
import org.opsli.common.exception.ServiceException;
import org.opsli.common.utils.FieldUtil;
import org.opsli.core.base.entity.HasChildren;
import org.opsli.core.base.service.impl.CrudServiceImpl;
import org.opsli.core.persistence.querybuilder.GenQueryBuilder;
import org.opsli.core.persistence.querybuilder.QueryBuilder;
import org.opsli.modulars.system.SystemMsg;

import org.opsli.modulars.system.discipline.entity.SysDiscipline;
import org.opsli.modulars.system.discipline.mapper.SysDisciplineMapper;
import org.opsli.modulars.system.discipline.service.ISysDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class SysDisciplineServiceImpl extends CrudServiceImpl<SysDisciplineMapper, SysDiscipline, SysDisciplineModel>
        implements ISysDisciplineService {

    @Autowired(required = false)
    private SysDisciplineMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDisciplineModel insert(SysDisciplineModel model) {
        if(model == null){
            return null;
        }

        // 唯一验证
        boolean verificationByCode = this.uniqueVerificationByCode(model);
        if(!verificationByCode){
            // 重复
            throw new ServiceException(SystemMsg.EXCEPTION_DISCIPLINE_UNIQUE);
        }

        // 如果上级ID 为空 则默认为 0
        if(StringUtils.isEmpty(model.getParentId())){
            model.setParentId("0");
        }

        return super.insert(model);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysDisciplineModel update(SysDisciplineModel model) {
        if(model == null){
            return null;
        }

        // 唯一验证
        boolean verificationByCode = this.uniqueVerificationByCode(model);
        if(!verificationByCode){
            // 重复
            throw new ServiceException(SystemMsg.EXCEPTION_DISCIPLINE_UNIQUE);
        }

        return super.update(model);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(String id) {
        // 先删除子数据
        this.deleteByParentId(id);

        return super.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAll(String[] ids) {
        // 先删除子数据
        for (String id : ids) {
            this.deleteByParentId(id);
        }

        return super.deleteAll(ids);
    }

    /**
     * 逐级删除子数据
     * @param parentId 父级节点
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByParentId(String parentId) {
        boolean ret = false;
        QueryBuilder<SysDiscipline> queryBuilder = new GenQueryBuilder<>();
        QueryWrapper<SysDiscipline> queryWrapper = queryBuilder.build();
        queryWrapper.eq(FieldUtil.humpToUnderline(MyBatisConstants.FIELD_PARENT_ID), parentId);
        List<SysDiscipline> childList = super.findList(queryWrapper);
        for (SysDiscipline child : childList) {
            super.delete(child.getId());
            // 逐级删除子数据
            ret = this.deleteByParentId(child.getId());
        }
        return ret;
    }


    /**
     * 唯一验证
     * @param model model
     * @return Integer
     */
    @Transactional(readOnly = true)
    public boolean uniqueVerificationByCode(SysDisciplineModel model){
        if(model == null){
            return false;
        }
        QueryWrapper<SysDiscipline> wrapper = new QueryWrapper<>();

        // code 唯一
        wrapper.eq("discipline_code", model.getDisciplineCode());

        // 重复校验排除自身
        if(StringUtils.isNotEmpty(model.getId())){
            wrapper.notIn(MyBatisConstants.FIELD_ID, model.getId());
        }

        return super.count(wrapper) == 0;
    }


    /**
     * 是否有下级
     * @param parentIds 父级节点集合
     * @return List
     */
    @Override
    @Transactional(readOnly = true)
    public List<HasChildren> hasChildren(Set<String> parentIds){
        if(CollUtil.isEmpty(parentIds)){
            return null;
        }
        QueryWrapper<SysDiscipline> wrapper = new QueryWrapper<>();

        wrapper.in(FieldUtil.humpToUnderline(MyBatisConstants.FIELD_PARENT_ID), parentIds)
                .eq(MyBatisConstants.FIELD_DELETE_LOGIC,  DictType.NO_YES_NO.getValue())
                .groupBy(FieldUtil.humpToUnderline(MyBatisConstants.FIELD_PARENT_ID));

        return mapper.hasChildren(wrapper);
    }
}
