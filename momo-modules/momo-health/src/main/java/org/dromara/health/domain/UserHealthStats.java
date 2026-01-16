package org.dromara.health.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 用户健康体征历史对象 user_health_stats
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_health_stats")
public class UserHealthStats extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 关联用户ID
     */
    private Long userId;

    /**
     * 当前身高(cm)
     */
    private Double height;

    /**
     * 当前体重(kg)
     */
    private Double weight;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * 记录日期
     */
    private Date recordDate;

    /**
     * 备注
     */
    private String remark;


}
