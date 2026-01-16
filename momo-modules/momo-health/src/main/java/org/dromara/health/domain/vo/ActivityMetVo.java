package org.dromara.health.domain.vo;

import org.dromara.health.domain.ActivityMet;
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
 * 运动类型MET参考视图对象 activity_met
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ActivityMet.class)
public class ActivityMetVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 运动名称（中文），例：慢跑
     */
    @ExcelProperty(value = "运动名称", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "中=文")
    private String activityName;

    /**
     * 英文名（可选），用于对齐来源
     */
    @ExcelProperty(value = "英文名", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "可=选")
    private String activityNameEn;

    /**
     * 大类，例：跑步/骑行/力量
     */
    @ExcelProperty(value = "大类，例：跑步/骑行/力量")
    private String category;

    /**
     * 子类，例：户外/跑步机
     */
    @ExcelProperty(value = "子类，例：户外/跑步机")
    private String subcategory;

    /**
     * 代表MET，例：8.30
     */
    @ExcelProperty(value = "代表MET，例：8.30")
    private Double metValue;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
