package org.dromara.health.domain.bo;

import org.dromara.health.domain.FoodCategory;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 食物分类业务对象 food_category
 *
 * @author Lion Li
 * @date 2026-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FoodCategory.class, reverseConvertGenerate = false)
public class FoodCategoryBo extends BaseEntity {

    /**
     * 分类ID (手动分配/雪花ID)
     */
    @NotNull(message = "分类ID (手动分配/雪花ID)不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 排序权重
     */
    private Long sortOrder;

    /**
     * 备注
     */
    private String remark;


}
