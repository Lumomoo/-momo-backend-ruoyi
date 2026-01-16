package org.dromara.health.domain.vo;

import org.dromara.health.domain.FoodInfo;
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
 * 食物营养成分视图对象 food_info
 *
 * @author Lomomo
 * @date 2026-01-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FoodInfo.class)
public class FoodInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 食物ID (手动分配/雪花ID)
     */
    @ExcelProperty(value = "食物ID (手动分配/雪花ID)")
    private Long id;

    /**
     * 所属分类ID
     */
    @ExcelProperty(value = "所属分类ID")
    private Long categoryId;

    /**
     * 所属分类名称
     */
    @Translation(type = HealthConstant.CATEGORY_ID_TO_NAME, mapper = "categoryId")
    @ExcelProperty(value = "所属分类名称")
    private String categoryName;

    /**
     * 食物名称
     */
    @ExcelProperty(value = "食物名称")
    private String name;

    /**
     * 品牌/厂家
     */
    @ExcelProperty(value = "品牌/厂家")
    private String brand;

    /**
     * 热量(kcal)
     */
    @ExcelProperty(value = "热量(kcal)")
    private Double calories;

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
