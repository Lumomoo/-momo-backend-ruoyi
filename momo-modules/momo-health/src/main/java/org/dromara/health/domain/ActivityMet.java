package org.dromara.health.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 运动类型MET参考对象 activity_met
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("activity_met")
public class ActivityMet extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 运动名称（中文），例：慢跑
     */
    private String activityName;

    /**
     * 英文名（可选），用于对齐来源
     */
    private String activityNameEn;

    /**
     * 大类，例：跑步/骑行/力量
     */
    private String category;

    /**
     * 子类，例：户外/跑步机
     */
    private String subcategory;

    /**
     * 代表MET，例：8.30
     */
    private Double metValue;

    /**
     * 备注
     */
    private String remark;


}
