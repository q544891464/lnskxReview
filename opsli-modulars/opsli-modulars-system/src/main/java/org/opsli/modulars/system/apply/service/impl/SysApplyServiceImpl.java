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
package org.opsli.modulars.system.apply.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.opsli.common.constants.MyBatisConstants;
import org.opsli.common.utils.FieldUtil;
import org.opsli.modulars.system.tenant.entity.SysTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.opsli.core.base.service.impl.CrudServiceImpl;

import org.opsli.modulars.system.apply.entity.SysApply;
import org.opsli.api.wrapper.system.apply.SysApplyModel;
import org.opsli.modulars.system.apply.service.ISysApplyService;
import org.opsli.modulars.system.apply.mapper.SysApplyMapper;

import java.util.Collections;


/**
 * 申请表 Service Impl
 *
 * @author chinm
 * @date 2022-04-25 15:31:43
 */
@Service
public class SysApplyServiceImpl extends CrudServiceImpl<SysApplyMapper, SysApply, SysApplyModel>
    implements ISysApplyService {

    @Autowired(required = false)
    private SysApplyMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean IsPassApply(String applyId, String isPass){
        SysApplyModel model = this.get(applyId);

        if(model == null){
            return false;
        }

        UpdateWrapper<SysApply> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_pass", isPass)
                .eq(
                        FieldUtil.humpToUnderline(MyBatisConstants.FIELD_ID), applyId);
        this.update(updateWrapper);
        return true;
    }

}