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
import org.dromara.health.domain.vo.UserWeightGoalsVo;
import org.dromara.health.domain.bo.UserWeightGoalsBo;
import org.dromara.health.service.IUserWeightGoalsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户减肥目标
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/health/weightGoals")
public class UserWeightGoalsController extends BaseController {

    private final IUserWeightGoalsService userWeightGoalsService;

    /**
     * 查询用户减肥目标列表
     */
    @SaCheckPermission("health:weightGoals:list")
    @GetMapping("/list")
    public TableDataInfo<UserWeightGoalsVo> list(UserWeightGoalsBo bo, PageQuery pageQuery) {
        return userWeightGoalsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户减肥目标列表
     */
    @SaCheckPermission("health:weightGoals:export")
    @Log(title = "用户减肥目标", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserWeightGoalsBo bo, HttpServletResponse response) {
        List<UserWeightGoalsVo> list = userWeightGoalsService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户减肥目标", UserWeightGoalsVo.class, response);
    }

    /**
     * 获取用户减肥目标详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("health:weightGoals:query")
    @GetMapping("/{id}")
    public R<UserWeightGoalsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(userWeightGoalsService.queryById(id));
    }

    /**
     * 新增用户减肥目标
     */
    @SaCheckPermission("health:weightGoals:add")
    @Log(title = "用户减肥目标", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody UserWeightGoalsBo bo) {
        return toAjax(userWeightGoalsService.insertByBo(bo));
    }

    /**
     * 修改用户减肥目标
     */
    @SaCheckPermission("health:weightGoals:edit")
    @Log(title = "用户减肥目标", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody UserWeightGoalsBo bo) {
        return toAjax(userWeightGoalsService.updateByBo(bo));
    }

    /**
     * 删除用户减肥目标
     *
     * @param ids 主键串
     */
    @SaCheckPermission("health:weightGoals:remove")
    @Log(title = "用户减肥目标", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(userWeightGoalsService.deleteWithValidByIds(List.of(ids), true));
    }
}
