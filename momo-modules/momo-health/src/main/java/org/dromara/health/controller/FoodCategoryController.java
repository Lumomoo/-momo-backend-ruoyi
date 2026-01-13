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
import org.dromara.health.domain.vo.FoodCategoryVo;
import org.dromara.health.domain.bo.FoodCategoryBo;
import org.dromara.health.service.IFoodCategoryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 食物分类
 *
 * @author Lion Li
 * @date 2026-01-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/health/category")
public class FoodCategoryController extends BaseController {

    private final IFoodCategoryService foodCategoryService;

    /**
     * 查询食物分类列表
     */
    @SaCheckPermission("health:category:list")
    @GetMapping("/list")
    public TableDataInfo<FoodCategoryVo> list(FoodCategoryBo bo, PageQuery pageQuery) {
        return foodCategoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出食物分类列表
     */
    @SaCheckPermission("health:category:export")
    @Log(title = "食物分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FoodCategoryBo bo, HttpServletResponse response) {
        List<FoodCategoryVo> list = foodCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "食物分类", FoodCategoryVo.class, response);
    }

    /**
     * 获取食物分类详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("health:category:query")
    @GetMapping("/{id}")
    public R<FoodCategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(foodCategoryService.queryById(id));
    }

    /**
     * 新增食物分类
     */
    @SaCheckPermission("health:category:add")
    @Log(title = "食物分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FoodCategoryBo bo) {
        return toAjax(foodCategoryService.insertByBo(bo));
    }

    /**
     * 修改食物分类
     */
    @SaCheckPermission("health:category:edit")
    @Log(title = "食物分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FoodCategoryBo bo) {
        return toAjax(foodCategoryService.updateByBo(bo));
    }

    /**
     * 删除食物分类
     *
     * @param ids 主键串
     */
    @SaCheckPermission("health:category:remove")
    @Log(title = "食物分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(foodCategoryService.deleteWithValidByIds(List.of(ids), true));
    }
}
