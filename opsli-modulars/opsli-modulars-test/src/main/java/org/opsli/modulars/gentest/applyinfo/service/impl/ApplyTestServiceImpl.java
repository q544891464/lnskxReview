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
package org.opsli.modulars.gentest.applyinfo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.opsli.core.base.service.impl.CrudServiceImpl;

import org.opsli.modulars.gentest.applyinfo.entity.ApplyTest;
import org.opsli.api.wrapper.gentest.applyinfo.ApplyTestModel;
import org.opsli.modulars.gentest.applyinfo.service.IApplyTestService;
import org.opsli.modulars.gentest.applyinfo.mapper.ApplyTestMapper;


/**
 * 申请表信息 Service Impl
 *
 * @author chinm
 * @date 2022-04-21 15:59:11
 */
@Service
public class ApplyTestServiceImpl extends CrudServiceImpl<ApplyTestMapper, ApplyTest, ApplyTestModel>
    implements IApplyTestService {

    @Autowired(required = false)
    private ApplyTestMapper mapper;

}