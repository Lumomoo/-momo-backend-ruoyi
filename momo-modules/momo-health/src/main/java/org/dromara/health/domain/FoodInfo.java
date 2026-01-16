package org.dromara.health.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 食物营养成分对象 food_info
 *
 * @author Lomomo
 * @date 2026-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("food_info")
public class FoodInfo extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 食物ID (手动分配/雪花ID)
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 所属分类ID
     */
    private Long categoryId;

    /**
     * 食物名称
     */
    private String name;

    /**
     * 品牌/厂家
     */
    private String brand;

    /**
     * 热量(kcal)
     */
    private Double calories;

    /**
     * 蛋白质(g)
     */
    private Double protein;

    /**
     * 脂肪(g)
     */
    private Double fat;

    /**
     * 碳水化合物(g)
     */
    private Double carbohydrate;

    /**
     * 膳食纤维(g)
     */
    private Double fiber;

    /**
     * 备注
     */
    private String remark;


}
