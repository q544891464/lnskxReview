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
package org.opsli.modulars.system.apply.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.core.base.entity.BaseEntity;

/**
 * 申请表 Entity
 *
 * @author chinm
 * @date 2022-04-25 15:31:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysApply extends BaseEntity {


    /** 申请项目名称 */
    private String applyName;

    /** 组织id */
    private String orgId;

    /** 申请用户id */
    private String applicantId;

    /** 是否通过:0未通过，1通过 */
    private String isPass;

    /** 主要完成人 */
    private String mainAuthors;

    /** 关键词 */
    private String keywords;

    /** 任务来源 */
    private String taskSource;

    /** 具体计划、基金名称和编号 */
    private String fundName;

    /** 成果简介 */
    private String introduction;

    /** 成果原创性、前沿性、突破性创新内容和学术贡献 */
    private String innovation;

    /** 成果应用情况 */
    private String application;

    // ========================================




    /** 逻辑删除字段 */
    @TableLogic
    private Integer deleted;

    /** 多租户字段 */
    private String tenantId;



}