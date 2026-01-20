package org.dromara.health.domain.vo;

import org.dromara.health.domain.UserActiveLogs;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.health.common.constant.HealthConstant;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 用户活动记录视图对象 user_active_logs
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = UserActiveLogs.class)
public class UserActiveLogsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 关联食物营养库ID
     */
    @ExcelProperty(value = "关联食物营养库ID")
    private Long foodId;

    /**
     * 食物名称
     */
    @Translation(type = HealthConstant.FOOD_ID_TO_NAME, mapper = "foodId")
    @ExcelProperty(value = "食物名称")
    private String foodName;

    /**
     * 关联运动类型库ID
     */
    @ExcelProperty(value = "关联运动类型库ID")
    private Long exerciseId;

    /**
     * 活动类型：1-饮食，2-运动
     */
    @ExcelProperty(value = "活动类型：1-饮食，2-运动")
    private Long activeType;

    /**
     * 餐次: 1-早餐, 2-早加餐, 3-午餐, 4-午加餐, 5-晚餐, 6-晚加餐；仅饮食需要
     */
    @ExcelProperty(value = "餐次: 1-早餐, 2-早加餐, 3-午餐, 4-午加餐, 5-晚餐, 6-晚加餐；仅饮食需要")
    private Long mealType;

    /**
     * 食用分量（克)
     */
    @ExcelProperty(value = "食用分量", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "食用分量（克)")
    private Double foodAmount;

    /**
     * 运动时长（分钟)
     */
    @ExcelProperty(value = "运动时长", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "运动时长（分钟)")
    private Double exerciseAmount;

    /**
     * 本次活动摄入/扣件的总热量(kcal)
     */
    @ExcelProperty(value = "本次活动摄入/扣件的总热量(kcal)")
    private Double totalCalories;

    /**
     * 蛋白质(g)
     */
    @ExcelProperty(value = "蛋白质(g)")
    private Double protein;

    /**
     * 脂肪(g)
     */
    @ExcelProperty(value = "脂肪(g)")
    private Double fat;

    /**
     * 碳水化合物(g)
     */
    @ExcelProperty(value = "碳水化合物(g)")
    private Double carbohydrate;

    /**
     * 膳食纤维(g)
     */
    @ExcelProperty(value = "膳食纤维(g)")
    private Double fiber;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
