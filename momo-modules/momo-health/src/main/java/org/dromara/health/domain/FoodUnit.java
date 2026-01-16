package org.dromara.health.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 食物单位换算对象 food_unit
 *
 * @author Lomomo
 * @date 2026-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("food_unit")
public class FoodUnit extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 单位ID (手动分配/雪花ID)
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 对应食物ID
     */
    private Long foodId;

    /**
     * 单位名称(如:个/包/碗)
     */
    private String unitName;

    /**
     * 对应重量(g)
     */
    private Double weightGram;

    /**
     * 备注
     */
    private String remark;


}
