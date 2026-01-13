package org.dromara.health.domain.vo;

import org.dromara.health.domain.FoodUnit;
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
 * 食物单位换算视图对象 food_unit
 *
 * @author Lomomo
 * @date 2026-01-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FoodUnit.class)
public class FoodUnitVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 单位ID (手动分配/雪花ID)
     */
    @ExcelProperty(value = "单位ID (手动分配/雪花ID)")
    private Long id;

    /**
     * 对应食物ID
     */
    @ExcelProperty(value = "对应食物ID")
    private Long foodId;

    /**
     * 单位名称(如:个/包/碗)
     */
    @ExcelProperty(value = "单位名称(如:个/包/碗)")
    private String unitName;

    /**
     * 对应重量(g)
     */
    @ExcelProperty(value = "对应重量(g)")
    private Long weightGram;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
