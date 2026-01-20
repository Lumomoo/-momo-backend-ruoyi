package org.dromara.health.domain.bo;

import org.dromara.health.domain.UserActiveLogs;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户活动记录业务对象 user_active_logs
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserActiveLogs.class, reverseConvertGenerate = false)
public class UserActiveLogsBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
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
    @NotNull(message = "活动类型：1-饮食，2-运动不能为空", groups = { AddGroup.class, EditGroup.class })
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
