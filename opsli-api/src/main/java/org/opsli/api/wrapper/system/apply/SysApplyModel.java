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
package org.opsli.api.wrapper.system.apply;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.api.base.warpper.ApiWrapper;
import org.opsli.common.annotation.validator.Validator;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.common.annotation.validator.ValidatorLenMin;
import org.opsli.common.enums.ValidatorType;
import org.opsli.plugins.excel.annotation.ExcelInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 申请表 Model
*
* @author chinm
* @date 2022-04-27 09:18:03
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysApplyModel extends ApiWrapper {

    /** 申请uid */
    @ApiModelProperty(value = "申请uid")
    @ExcelProperty(value = "申请uid", order = 6)
    @ExcelIgnore
    @ValidatorLenMax(50)
    private String uid;

    /** 申请项目名称 */
    @ApiModelProperty(value = "申请项目名称")
    @ExcelProperty(value = "申请项目名称", order = 5)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String applyName;

    /** 申请用户id */
    @ApiModelProperty(value = "申请用户id")
    @ExcelProperty(value = "申请用户id", order = 6)
    @ExcelIgnore
    @ValidatorLenMax(19)
    private String applicantId;

    /** 组织id */
    @ApiModelProperty(value = "组织id")
    @ExcelProperty(value = "组织id", order = 6)
    @ExcelIgnore
    @ValidatorLenMax(19)
    private String orgId;

    /** 是否通过:0未通过，1通过 */
    @ApiModelProperty(value = "是否通过:0未通过，1通过")
    @ExcelProperty(value = "是否通过:0未通过，1通过", order = 11)
    @ExcelIgnore
    @ValidatorLenMax(1)
    private String isPass;

    /** 主要完成人 */
    @ApiModelProperty(value = "主要完成人")
    @ExcelProperty(value = "主要完成人", order = 12)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String mainAuthors;

    /** 成果类别 */
    @ApiModelProperty(value = "成果类别")
    @ExcelProperty(value = "成果类别", order = 13)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String applyType;

    /** 学科专业 */
    /** TODO: 目前是以Json形式存储，在Excel中忽略该项，若要在Excel中显示可添加converter转换，参照PrizeConverter */
    @ApiModelProperty(value = "学科专业")
    @ExcelProperty(value = "学科专业", order = 13)
    @ExcelIgnore
    @ValidatorLenMax(255)
    private String discipline;

    /** 发表日期 */
    @ApiModelProperty(value = "发表日期")
    @ExcelProperty(value = "发表日期", order = 14)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String publicationDate;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    @ExcelProperty(value = "关键词", order = 15)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String keywords;

    /** 任务来源 */
    @ApiModelProperty(value = "任务来源")
    @ExcelProperty(value = "任务来源", order = 16)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String taskSource;

    /** 具体计划、基金名称和编号 */
    @ApiModelProperty(value = "具体计划、基金名称和编号")
    @ExcelProperty(value = "具体计划、基金名称和编号", order = 17)
    @ExcelInfo
    @ValidatorLenMax(255)
    private String fundName;

    /** 成果简介 */
    @ApiModelProperty(value = "成果简介")
    @ExcelProperty(value = "成果简介", order = 18)
    @ExcelInfo
    private String introduction;

    /** 成果原创性、前沿性、突破性创新内容和学术贡献 */
    @ApiModelProperty(value = "成果原创性、前沿性、突破性创新内容和学术贡献")
    @ExcelProperty(value = "成果原创性、前沿性、突破性创新内容和学术贡献", order = 19)
    @ExcelInfo
    private String innovation;

    /** 成果应用情况 */
    @ApiModelProperty(value = "成果应用情况")
    @ExcelProperty(value = "成果应用情况", order = 20)
    @ExcelInfo
    private String application;

    /** 第一作者 */
    @ApiModelProperty(value = "第一作者")
    @ExcelProperty(value = "第一作者", order = 21)
    @ExcelInfo
    private String firstAuthor;

    /** 第一作者性别 */
    @ApiModelProperty(value = "第一作者性别")
    @ExcelProperty(value = "第一作者性别", order = 22)
    @ExcelInfo
    private String firstAuthorSex;

    /** 第一作者出生 */
    @ApiModelProperty(value = "第一作者出生")
    @ExcelProperty(value = "第一作者出生", order = 22)
    @ExcelInfo
    private String firstAuthorBirth;

    /** 第一作者学历 */
    @ApiModelProperty(value = "第一作者学历")
    @ExcelProperty(value = "第一作者学历", order = 22)
    @ExcelInfo
    private String firstAuthorEdu;

    /** 第一作者学位 */
    @ApiModelProperty(value = "第一作者学位")
    @ExcelProperty(value = "第一作者学位", order = 22)
    @ExcelInfo
    private String firstAuthorDegree;

    /** 第一作者毕业学校 */
    @ApiModelProperty(value = "第一作者毕业学校")
    @ExcelProperty(value = "第一作者毕业学校", order = 22)
    @ExcelInfo
    private String firstAuthorSchool;

    /** 第一作者专业 */
    @ApiModelProperty(value = "第一作者专业")
    @ExcelProperty(value = "第一作者专业", order = 22)
    @ExcelInfo
    private String firstAuthorProfess;


    /** 第一作者身份证号 */
    @ApiModelProperty(value = "第一作者身份证")
    @ExcelProperty(value = "第一作者身份证", order = 23)
    @ExcelInfo
    private String firstAuthorId;

    /** 第一作者地域 */
    @ApiModelProperty(value = "第一作者地域")
    @ExcelProperty(value = "第一作者地域", order = 24)
    @ExcelInfo
    private String firstAuthorRegion;

    /** 第一作者工作单位 */
    @ApiModelProperty(value = "第一作者工作单位")
    @ExcelProperty(value = "第一作者工作单位", order = 25)
    @ExcelInfo
    private String firstAuthorWorkplace;

    /** 第一作者邮箱 */
    @ApiModelProperty(value = "第一作者邮箱")
    @ExcelProperty(value = "第一作者邮箱", order = 26)
    @ExcelInfo
    private String firstAuthorMail;

    /** 第一作者邮编*/
    @ApiModelProperty(value = "第一作者邮编")
    @ExcelProperty(value = "第一作者邮编", order = 26)
    @ExcelInfo
    private String firstAuthorCode;

    /** 第一作者手机号 */
    @ApiModelProperty(value = "第一作者手机号")
    @ExcelProperty(value = "第一作者手机号", order = 27)
    @ExcelInfo
    private String firstAuthorTel;

    /** 第一作者职务 */
    @ApiModelProperty(value = "第一作者职务")
    @ExcelProperty(value = "第一作者职务", order = 28)
    @ExcelInfo
    private String firstAuthorPost;

    /** 第一作者职称 */
    @ApiModelProperty(value = "第一作者职称")
    @ExcelProperty(value = "第一作者职称", order = 29)
    @ExcelInfo
    private String firstAuthorTitle;

    /** 第一作者行政级别 */
    @ApiModelProperty(value = "第一作者行政级别")
    @ExcelProperty(value = "第一作者行政级别", order = 30)
    @ExcelInfo
    private String firstAuthorAdminLevel;

    /** 第一作者职称级别 */
    @ApiModelProperty(value = "第一作者职称级别")
    @ExcelProperty(value = "第一作者职称级别", order = 31)
    @ExcelInfo
    private String firstAuthorTitleLevel;

    /** 第一作者地址*/
    @ApiModelProperty(value = "第一作者地址")
    @ExcelProperty(value = "第一作者地址", order = 27)
    @ExcelInfo
    private String firstAuthorAddress;

    /** 第一作者简介 */
    @ApiModelProperty(value = "第一作者简介")
    @ExcelProperty(value = "第一作者简介", order = 32)
    @ExcelInfo
    private String firstAuthorIntro;

    /** 论文发表刊物名称 */
    @ApiModelProperty(value = "论文发表刊物名称")
    @ExcelProperty(value = "论文发表刊物名称", order = 22)
    @ExcelIgnore
    private String publicationName;

    /** 刊物影响因子 */
    @ApiModelProperty(value = "刊物影响因子")
    @ExcelProperty(value = "刊物影响因子", order = 22)
    @ExcelIgnore
    private String impactFactor;

    /** 检索收录情况 */
    @ApiModelProperty(value = "检索收录情况")
    @ExcelProperty(value = "检索收录情况", order = 22)
    @ExcelIgnore
    private String retrieval;

    /** 被引用次数 */
    @ApiModelProperty(value = "被引用次数")
    @ExcelProperty(value = "被引用次数", order = 22)
    @ExcelIgnore
    private String citations;

    /** 论文类型 */
    @ApiModelProperty(value = "论文类型")
    @ExcelProperty(value = "论文类型", order = 22)
    @ExcelIgnore
    private String paperType;

    /** 相关系列成果 */
    @ApiModelProperty(value = "相关系列成果")
    @ExcelProperty(value = "相关系列成果", order = 22)
    @ExcelIgnore
    private String relatedAchievements;

    /** 产生该成果的项目名称 */
    @ApiModelProperty(value = "产生该成果的项目名称")
    @ExcelProperty(value = "产生该成果的项目名称", order = 22)
    @ExcelIgnore
    private String projectName;

    /** 项目级别 */
    @ApiModelProperty(value = "项目级别")
    @ExcelProperty(value = "项目级别", order = 22)
    @ExcelIgnore
    private String projectLevel;

    /** 自主创新情况 */
    @ApiModelProperty(value = "自主创新情况")
    @ExcelProperty(value = "自主创新情况", order = 22)
    @ExcelIgnore
    private String projectInnovation;


    /** 文件存储地址 */
    @ApiModelProperty(value = "文件存储地址")
    @ExcelProperty(value = "文件存储地址", order = 21)
    @ExcelIgnore
    private String filePath;

    /** word文件存储地址 */
    @ApiModelProperty(value = "word文件存储地址")
    @ExcelProperty(value = "word文件存储地址", order = 21)
    @ExcelIgnore
    private String wordPath;

    /** 其他证明材料存储地址 */
    @ApiModelProperty(value = "其他证明材料存储地址")
    @ExcelProperty(value = "其他证明材料存储地址", order = 21)
    @ExcelIgnore
    private String otherFilePath;

    /** 已完成材料存储地址 */
    @ApiModelProperty(value = "已完成材料存储地址")
    @ExcelProperty(value = "已完成材料存储地址", order = 21)
    @ExcelIgnore
    private String completedFilePath;

    /** 奖项 */
    @ApiModelProperty(value = "奖项")
    @ExcelProperty(value = "奖项", order = 22,converter = PrizeConverter.class)
    @ExcelInfo
    private String prize;

    /** 第二作者 */
    @ApiModelProperty(value = "第二作者")
    @ExcelProperty(value = "第二作者", order = 22)
    @ExcelIgnore
    private String author2;

    /** 第二作者 */
    @ApiModelProperty(value = "第二作者")
    @ExcelProperty(value = "第二作者", order = 22)
    @ExcelIgnore
    private String author2Sex;

    /** 第二作者 */
    @ApiModelProperty(value = "第二作者")
    @ExcelProperty(value = "第二作者", order = 22)
    @ExcelIgnore
    private String author2Workplace;

    /** 第二作者 */
    @ApiModelProperty(value = "第二作者")
    @ExcelProperty(value = "第二作者", order = 22)
    @ExcelIgnore
    private String author2Tel;

    /** 第三作者 */
    @ApiModelProperty(value = "第三作者")
    @ExcelProperty(value = "第三作者", order = 22)
    @ExcelIgnore
    private String author3;

    /** 第三作者 */
    @ApiModelProperty(value = "第三作者")
    @ExcelProperty(value = "第三作者", order = 22)
    @ExcelIgnore
    private String author3Sex;

    /** 第三作者 */
    @ApiModelProperty(value = "第三作者")
    @ExcelProperty(value = "第三作者", order = 22)
    @ExcelIgnore
    private String author3Workplace;

    /** 第三作者 */
    @ApiModelProperty(value = "第三作者")
    @ExcelProperty(value = "第三作者", order = 22)
    @ExcelIgnore
    private String author3Tel;

    /** 第四作者 */
    @ApiModelProperty(value = "第四作者")
    @ExcelProperty(value = "第四作者", order = 22)
    @ExcelIgnore
    private String author4;

    /** 第四作者 */
    @ApiModelProperty(value = "第四作者")
    @ExcelProperty(value = "第四作者", order = 22)
    @ExcelIgnore
    private String author4Sex;

    /** 第四作者 */
    @ApiModelProperty(value = "第四作者")
    @ExcelProperty(value = "第四作者", order = 22)
    @ExcelIgnore
    private String author4Workplace;

    /** 第四作者 */
    @ApiModelProperty(value = "第四作者")
    @ExcelProperty(value = "第四作者", order = 22)
    @ExcelIgnore
    private String author4Tel;




}