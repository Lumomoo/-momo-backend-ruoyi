package org.dromara.health.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.health.domain.UserHealthStats;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 用户健康体征历史视图对象 user_health_stats
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = UserHealthStats.class)
public class UserHealthStatsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 关联用户ID
     */
    @ExcelProperty(value = "关联用户ID")
    private Long userId;

    /**
     * 当前身高(cm)
     */
    @ExcelProperty(value = "当前身高(cm)")
    private Double height;

    /**
     * 当前体重(kg)
     */
    @ExcelProperty(value = "当前体重(kg)")
    private Double weight;

    /**
     * 血型
     */
    @ExcelProperty(value = "血型")
    private String bloodType;

    /**
     * 记录日期
     */
    @ExcelProperty(value = "记录日期")
    private Date recordDate;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
