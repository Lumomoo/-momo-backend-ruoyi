package org.dromara.health.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.health.domain.vo.FoodUnitVo;
import org.dromara.health.domain.bo.FoodUnitBo;
import org.dromara.health.service.IFoodUnitService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 食物单位换算
 *
 * @author Lomomo
 * @date 2026-01-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/health/unit")
public class FoodUnitController extends BaseController {

    private final IFoodUnitService foodUnitService;

    /**
     * 查询食物单位换算列表
     */
    @SaCheckPermission("health:unit:list")
    @GetMapping("/list")
    public TableDataInfo<FoodUnitVo> list(FoodUnitBo bo, PageQuery pageQuery) {
        return foodUnitService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出食物单位换算列表
     */
    @SaCheckPermission("health:unit:export")
    @Log(title = "食物单位换算", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FoodUnitBo bo, HttpServletResponse response) {
        List<FoodUnitVo> list = foodUnitService.queryList(bo);
        ExcelUtil.exportExcel(list, "食物单位换算", FoodUnitVo.class, response);
    }

    /**
     * 获取食物单位换算详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("health:unit:query")
    @GetMapping("/{id}")
    public R<FoodUnitVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(foodUnitService.queryById(id));
    }

    /**
     * 新增食物单位换算
     */
    @SaCheckPermission("health:unit:add")
    @Log(title = "食物单位换算", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FoodUnitBo bo) {
        return toAjax(foodUnitService.insertByBo(bo));
    }

    /**
     * 修改食物单位换算
     */
    @SaCheckPermission("health:unit:edit")
    @Log(title = "食物单位换算", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FoodUnitBo bo) {
        return toAjax(foodUnitService.updateByBo(bo));
    }

    /**
     * 删除食物单位换算
     *
     * @param ids 主键串
     */
    @SaCheckPermission("health:unit:remove")
    @Log(title = "食物单位换算", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(foodUnitService.deleteWithValidByIds(List.of(ids), true));
    }
}
