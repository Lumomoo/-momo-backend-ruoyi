package org.dromara.health.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 用户资料详情对象 user_profiles
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_profiles")
public class UserProfiles extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资料记录ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 关联用户ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 性别: 0-未知, 1-男, 2-女
     */
    private Long gender;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 所在地区
     */
    private String region;

    /**
     * 个人签名
     */
    private String bio;

    /**
     * 备注
     */
    private String remark;


}
