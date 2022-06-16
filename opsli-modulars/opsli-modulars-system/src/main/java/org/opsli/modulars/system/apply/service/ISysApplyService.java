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
package org.opsli.modulars.system.apply.service;

import org.opsli.core.base.service.interfaces.CrudServiceInterface;


import org.opsli.modulars.system.apply.entity.SysApply;
import org.opsli.api.wrapper.system.apply.SysApplyModel;

/**
 * 申请表 Service
 *
 * @author chinm
 * @date 2022-04-25 15:31:43
 */
public interface ISysApplyService extends CrudServiceInterface<SysApply, SysApplyModel> {

    /**
     * 变更通过状态
     *
     * @param applyId 申请ID
     * @param isPass 通过状态
     * @return boolean
     */
    boolean IsPassApply(String applyId, String isPass);

    /**
     * 变更奖项
     *
     * @param applyId 申请ID
     * @param prize 通过状态
     * @return boolean
     */
    boolean SetPrize(String applyId, String prize);
}