package org.dromara.health.domain.bo;

import org.dromara.health.domain.UserWeightGoals;
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
 * 用户减肥目标业务对象 user_weight_goals
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserWeightGoals.class, reverseConvertGenerate = false)
public class UserWeightGoalsBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 关联用户ID
     */
    @NotNull(message = "关联用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 初始体重(kg) - 开启计划时的体重
     */
    @NotNull(message = "初始体重(kg) - 开启计划时的体重不能为空", groups = { AddGroup.class, EditGroup.class })
    private Double startWeight;

    /**
     * 目标体重(kg)
     */
    @NotNull(message = "目标体重(kg)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Double targetWeight;

    /**
     * 期望达成目标的日期
     */
    private Date targetDate;

    /**
     * 状态: 1-进行中, 2-已达成, 3-已放弃
     */
    private Long currentStatus;

    /**
     * 备注
     */
    private String remark;


}
