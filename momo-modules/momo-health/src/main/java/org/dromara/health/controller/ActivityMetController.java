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
import org.dromara.health.domain.vo.ActivityMetVo;
import org.dromara.health.domain.bo.ActivityMetBo;
import org.dromara.health.service.IActivityMetService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 运动类型MET参考
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/health/met")
public class ActivityMetController extends BaseController {

    private final IActivityMetService activityMetService;

    /**
     * 查询运动类型MET参考列表
     */
    @SaCheckPermission("health:met:list")
    @GetMapping("/list")
    public TableDataInfo<ActivityMetVo> list(ActivityMetBo bo, PageQuery pageQuery) {
        return activityMetService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出运动类型MET参考列表
     */
    @SaCheckPermission("health:met:export")
    @Log(title = "运动类型MET参考", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ActivityMetBo bo, HttpServletResponse response) {
        List<ActivityMetVo> list = activityMetService.queryList(bo);
        ExcelUtil.exportExcel(list, "运动类型MET参考", ActivityMetVo.class, response);
    }

    /**
     * 获取运动类型MET参考详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("health:met:query")
    @GetMapping("/{id}")
    public R<ActivityMetVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(activityMetService.queryById(id));
    }

    /**
     * 新增运动类型MET参考
     */
    @SaCheckPermission("health:met:add")
    @Log(title = "运动类型MET参考", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ActivityMetBo bo) {
        return toAjax(activityMetService.insertByBo(bo));
    }

    /**
     * 修改运动类型MET参考
     */
    @SaCheckPermission("health:met:edit")
    @Log(title = "运动类型MET参考", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ActivityMetBo bo) {
        return toAjax(activityMetService.updateByBo(bo));
    }

    /**
     * 删除运动类型MET参考
     *
     * @param ids 主键串
     */
    @SaCheckPermission("health:met:remove")
    @Log(title = "运动类型MET参考", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(activityMetService.deleteWithValidByIds(List.of(ids), true));
    }
}
