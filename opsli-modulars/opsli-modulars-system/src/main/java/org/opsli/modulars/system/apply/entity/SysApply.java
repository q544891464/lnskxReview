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

import com.alibaba.excel.annotation.ExcelIgnore;
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

    /** 申请项目uid */
    private String uid;

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

    /** 成果类别 */
    private String applyType;

    /** 发表日期 */
    private String publicationDate;

    /** 学科专业 */
    private String discipline;

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

    /** 第一作者 */
    private String firstAuthor;

    /** 第一作者性别 */
    private String firstAuthorSex;

    /** 第一作者出生 */
    private String firstAuthorBirth;

    /** 第一作者身份证 */
    private String firstAuthorId;

    /** 第一作者地域 */
    private String firstAuthorRegion;

    /** 第一作者工作单位 */
    private String firstAuthorWorkplace;

    /** 第一作者邮箱 */
    private String firstAuthorMail;

    /** 第一作者手机号 */
    private String firstAuthorTel;

    /** 第一作者职务 */
    private String firstAuthorPost;

    /** 第一作者职称 */
    private String firstAuthorTitle;

    /** 第一作者行政级别 */
    private String firstAuthorAdminLevel;

    /** 第一作者职称级别 */
    private String firstAuthorTitleLevel;

    /** 第一作者学历*/
    private String firstAuthorEdu;

    /** 第一作者学位*/
    private String firstAuthorDegree;

     /** 第一作者专业*/
    private String firstAuthorProfess;

    /** 第一作者邮编*/
    private String firstAuthorCode;

    /** 第一作者地址*/
    private String firstAuthorAddress;

    /** 第一作者毕业学校*/
    private String firstAuthorSchool;

    /** 第一作者简介 */
    private String firstAuthorIntro;


    /** 文件存储地址 */
    private String filePath;

    /** 奖项 */
    private String prize;

    /** 论文发表刊物名称 */
    private String publicationName;

    /** 刊物影响因子 */
    private String impactFactor;

    /** 检索收录情况 */
    private String retrieval;

    /** 被引用次数 */
    private String citations;

    /** 论文类型 */
    private String paperType;

    /** 相关系列成果 */
    private String relatedAchievements;

    /** 产生该成果的项目名称 */
    private String projectName;

    /** 项目级别 */
    private String projectLevel;

    /** 自主创新情况 */
    private String projectInnovation;

    /** word导出路径 */
    private String wordPath;

    /** 其他证明材料路径 */
    private String otherFilePath;

    /** 已完成材料存储路径 */
    private String completedFilePath;

    // ========================================




    /** 逻辑删除字段 */
    @TableLogic
    private Integer deleted;

    /** 多租户字段 */
    private String tenantId;



}