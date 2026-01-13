package org.dromara.health.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 食物分类对象 food_category
 *
 * @author Lion Li
 * @date 2026-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("food_category")
public class FoodCategory extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID (手动分配/雪花ID)
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 分类名称
     */
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
