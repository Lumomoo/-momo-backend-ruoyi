package org.dromara.health.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.health.domain.UserWeightGoals;
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
 * 用户减肥目标视图对象 user_weight_goals
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = UserWeightGoals.class)
public class UserWeightGoalsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 关联用户ID
     */
    @ExcelProperty(value = "关联用户ID")
    private Long userId;

    /**
     * 初始体重(kg) - 开启计划时的体重
     */
    @ExcelProperty(value = "初始体重(kg) - 开启计划时的体重")
    private Double startWeight;

    /**
     * 目标体重(kg)
     */
    @ExcelProperty(value = "目标体重(kg)")
    private Double targetWeight;

    /**
     * 期望达成目标的日期
     */
    @ExcelProperty(value = "期望达成目标的日期")
    private Date targetDate;

    /**
     * 状态: 1-进行中, 2-已达成, 3-已放弃
     */
    @ExcelProperty(value = "状态: 1-进行中, 2-已达成, 3-已放弃")
    private Long currentStatus;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
