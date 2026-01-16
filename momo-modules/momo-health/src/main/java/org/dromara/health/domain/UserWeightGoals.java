package org.dromara.health.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 用户减肥目标对象 user_weight_goals
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_weight_goals")
public class UserWeightGoals extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 关联用户ID
     */
    private Long userId;

    /**
     * 初始体重(kg) - 开启计划时的体重
     */
    private Double startWeight;

    /**
     * 目标体重(kg)
     */
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
