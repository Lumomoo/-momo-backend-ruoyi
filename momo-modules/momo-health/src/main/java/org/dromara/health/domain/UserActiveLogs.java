package org.dromara.health.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户活动记录对象 user_active_logs
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_active_logs")
public class UserActiveLogs extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 关联食物营养库ID
     */
    private Long foodId;

    /**
     * 关联运动类型库ID
     */
    private Long exerciseId;

    /**
     * 活动类型：1-饮食，2-运动
     */
    private Long activeType;

    /**
     * 餐次: 1-早餐, 2-早加餐, 3-午餐, 4-午加餐, 5-晚餐, 6-晚加餐；仅饮食需要
     */
    private Long mealType;

    /**
     * 食用分量（克)
     */
    private Double foodAmount;

    /**
     * 运动时长（分钟)
     */
    private Double exerciseAmount;

    /**
     * 本次活动摄入/扣件的总热量(kcal)
     */
    private Double totalCalories;

    /**
     * 备注
     */
    private String remark;


}
