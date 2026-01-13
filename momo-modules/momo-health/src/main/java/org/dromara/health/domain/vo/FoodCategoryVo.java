package org.dromara.health.domain.vo;

import org.dromara.health.domain.FoodCategory;
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
 * 食物分类视图对象 food_category
 *
 * @author Lion Li
 * @date 2026-01-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FoodCategory.class)
public class FoodCategoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID (手动分配/雪花ID)
     */
    @ExcelProperty(value = "分类ID (手动分配/雪花ID)")
    private Long id;

    /**
     * 分类名称
     */
    @ExcelProperty(value = "分类名称")
    private String name;

    /**
     * 排序权重
     */
    @ExcelProperty(value = "排序权重")
    private Long sortOrder;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
