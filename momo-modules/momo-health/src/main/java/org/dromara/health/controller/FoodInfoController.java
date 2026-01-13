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
import org.dromara.health.domain.vo.FoodInfoVo;
import org.dromara.health.domain.bo.FoodInfoBo;
import org.dromara.health.service.IFoodInfoService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 食物营养成分
 *
 * @author Lomomo
 * @date 2026-01-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/health/info")
public class FoodInfoController extends BaseController {

    private final IFoodInfoService foodInfoService;

    /**
     * 查询食物营养成分列表
     */
    @SaCheckPermission("health:info:list")
    @GetMapping("/list")
    public TableDataInfo<FoodInfoVo> list(FoodInfoBo bo, PageQuery pageQuery) {
        return foodInfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出食物营养成分列表
     */
    @SaCheckPermission("health:info:export")
    @Log(title = "食物营养成分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FoodInfoBo bo, HttpServletResponse response) {
        List<FoodInfoVo> list = foodInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "食物营养成分", FoodInfoVo.class, response);
    }

    /**
     * 获取食物营养成分详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("health:info:query")
    @GetMapping("/{id}")
    public R<FoodInfoVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(foodInfoService.queryById(id));
    }

    /**
     * 新增食物营养成分
     */
    @SaCheckPermission("health:info:add")
    @Log(title = "食物营养成分", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FoodInfoBo bo) {
        return toAjax(foodInfoService.insertByBo(bo));
    }

    /**
     * 修改食物营养成分
     */
    @SaCheckPermission("health:info:edit")
    @Log(title = "食物营养成分", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FoodInfoBo bo) {
        return toAjax(foodInfoService.updateByBo(bo));
    }

    /**
     * 删除食物营养成分
     *
     * @param ids 主键串
     */
    @SaCheckPermission("health:info:remove")
    @Log(title = "食物营养成分", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(foodInfoService.deleteWithValidByIds(List.of(ids), true));
    }
}
