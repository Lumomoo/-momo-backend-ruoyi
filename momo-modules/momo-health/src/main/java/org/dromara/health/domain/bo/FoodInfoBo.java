package org.dromara.health.domain.bo;

import org.dromara.health.domain.FoodInfo;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 食物营养成分业务对象 food_info
 *
 * @author Lomomo
 * @date 2026-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FoodInfo.class, reverseConvertGenerate = false)
public class FoodInfoBo extends BaseEntity {

    /**
     * 食物ID (手动分配/雪花ID)
     */
    @NotNull(message = "食物ID (手动分配/雪花ID)不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 所属分类ID
     */
    @NotNull(message = "所属分类ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long categoryId;

    /**
     * 食物名称
     */
    @NotBlank(message = "食物名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 品牌/厂家
     */
    private String brand;

    /**
     * 热量(kcal)
     */
    @NotNull(message = "热量(kcal)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long calories;

    /**
     * 蛋白质(g)
     */
    private Long protein;

    /**
     * 脂肪(g)
     */
    private Long fat;

    /**
     * 碳水化合物(g)
     */
    private Long carbohydrate;

    /**
     * 膳食纤维(g)
     */
    private Long fiber;

    /**
     * 备注
     */
    private String remark;


}
