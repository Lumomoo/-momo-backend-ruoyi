package org.dromara.health.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.health.domain.UserProfiles;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;



/**
 * 用户资料详情视图对象 user_profiles
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = UserProfiles.class)
public class UserProfilesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资料记录ID
     */
    @ExcelProperty(value = "资料记录ID")
    private Long id;

    /**
     * 关联用户ID
     */
    @ExcelProperty(value = "关联用户ID")
    private Long userId;

    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    @Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "userId")
    private String userName;

    /**
     * 最新减重目标
     */
    private UserWeightGoalsVo userWeightGoals;

    /**
     * 最新健康体征
     */
    private UserHealthStatsVo userHealthStats;

    /**
     * 昵称
     */
    @ExcelProperty(value = "昵称")
    private String nickname;

    /**
     * 头像地址
     */
    @ExcelProperty(value = "头像地址")
    private String avatarUrl;

    /**
     * 性别: 0-未知, 1-男, 2-女
     */
    @ExcelProperty(value = "性别: 0-未知, 1-男, 2-女")
    private Long gender;

    /**
     * 生日
     */
    @ExcelProperty(value = "生日")
    private Date birthday;

    /**
     * 所在地区
     */
    @ExcelProperty(value = "所在地区")
    private String region;

    /**
     * 个人签名
     */
    @ExcelProperty(value = "个人签名")
    private String bio;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
