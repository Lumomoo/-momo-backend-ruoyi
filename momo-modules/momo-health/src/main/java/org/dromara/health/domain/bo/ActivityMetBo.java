package org.dromara.health.domain.bo;

import org.dromara.health.domain.ActivityMet;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 运动类型MET参考业务对象 activity_met
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ActivityMet.class, reverseConvertGenerate = false)
public class ActivityMetBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 运动名称（中文），例：慢跑
     */
    @NotBlank(message = "运动名称（中文），例：慢跑不能为空", groups = { AddGroup.class, EditGroup.class })
    private String activityName;

    /**
     * 英文名（可选），用于对齐来源
     */
    private String activityNameEn;

    /**
     * 大类，例：跑步/骑行/力量
     */
    @NotBlank(message = "大类，例：跑步/骑行/力量不能为空", groups = { AddGroup.class, EditGroup.class })
    private String category;

    /**
     * 子类，例：户外/跑步机
     */
    private String subcategory;

    /**
     * 代表MET，例：8.30
     */
    @NotNull(message = "代表MET，例：8.30不能为空", groups = { AddGroup.class, EditGroup.class })
    private Double metValue;

    /**
     * 备注
     */
    private String remark;


}
