package org.dromara.health.domain.bo;

import org.dromara.health.domain.UserProfiles;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户资料详情业务对象 user_profiles
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserProfiles.class, reverseConvertGenerate = false)
public class UserProfilesBo extends BaseEntity {

    /**
     * 资料记录ID
     */
    @NotNull(message = "资料记录ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 关联用户ID
     */
    @NotNull(message = "关联用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
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
