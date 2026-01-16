package org.dromara.health.domain.bo;

import org.dromara.health.domain.FoodUnit;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 食物单位换算业务对象 food_unit
 *
 * @author Lomomo
 * @date 2026-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FoodUnit.class, reverseConvertGenerate = false)
public class FoodUnitBo extends BaseEntity {

    /**
     * 单位ID (手动分配/雪花ID)
     */
    @NotNull(message = "单位ID (手动分配/雪花ID)不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 对应食物ID
     */
    @NotNull(message = "对应食物ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long foodId;

    /**
     * 单位名称(如:个/包/碗)
     */
    @NotBlank(message = "单位名称(如:个/包/碗)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unitName;

    /**
     * 对应重量(g)
     */
    @NotNull(message = "对应重量(g)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Double weightGram;

    /**
     * 备注
     */
    private String remark;


}
