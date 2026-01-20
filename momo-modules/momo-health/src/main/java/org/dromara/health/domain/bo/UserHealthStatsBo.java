package org.dromara.health.domain.bo;

import org.dromara.health.domain.UserHealthStats;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户健康体征历史业务对象 user_health_stats
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserHealthStats.class, reverseConvertGenerate = false)
public class UserHealthStatsBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
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
    @NotNull(message = "当前体重(kg)不能为空", groups = { AddGroup.class, EditGroup.class })
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
