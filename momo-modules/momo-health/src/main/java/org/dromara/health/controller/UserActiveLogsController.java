package org.dromara.health.controller;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.dromara.health.domain.vo.UserActiveLogsSummaryVo;
import org.dromara.health.domain.vo.UserActiveLogsVo;
import org.dromara.health.domain.bo.UserActiveLogsBo;
import org.dromara.health.service.IUserActiveLogsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户活动记录
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/health/activeLogs")
public class UserActiveLogsController extends BaseController {

    private final IUserActiveLogsService userActiveLogsService;

    /**
     * 查询用户活动记录列表
     */
    @SaCheckPermission("health:activeLogs:list")
    @GetMapping("/list")
    public TableDataInfo<UserActiveLogsVo> list(UserActiveLogsBo bo, PageQuery pageQuery) {
        return userActiveLogsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户活动记录列表
     */
    @SaCheckPermission("health:activeLogs:export")
    @Log(title = "用户活动记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserActiveLogsBo bo, HttpServletResponse response) {
        List<UserActiveLogsVo> list = userActiveLogsService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户活动记录", UserActiveLogsVo.class, response);
    }

    /**
     * 获取用户活动记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("health:activeLogs:query")
    @GetMapping("/{id}")
    public R<UserActiveLogsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(userActiveLogsService.queryById(id));
    }

    /**
     * 获取用户最新一条活动记录
     *
     * @param userId 用户ID
     */
    @SaCheckPermission("health:activeLogs:query")
    @GetMapping("/user/{userId}/latest")
    public R<UserActiveLogsVo> getLatestByUserId(
            // 用户ID
            @NotNull(message = "用户ID不能为空")
            @PathVariable Long userId) {
        return R.ok(userActiveLogsService.queryLatestByUserId(userId));
    }

    /**
     * 获取用户指定日期的资料、健康体征与活动记录
     *
     * @param userId     用户ID
     * @param recordDate 查询日期
     */
    @SaCheckPermission("health:activeLogs:query")
    @GetMapping("/user/{userId}/summary")
    public R<UserActiveLogsSummaryVo> getSummaryByUserIdAndDate(
            // 用户ID
            @NotNull(message = "用户ID不能为空")
            @PathVariable Long userId,
            // 查询日期
            @NotNull(message = "日期不能为空")
            @RequestParam("date")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate recordDate) {
        return R.ok(userActiveLogsService.querySummaryByUserIdAndDate(userId, recordDate));
    }

    /**
     * 新增用户活动记录
     */
    @SaCheckPermission("health:activeLogs:add")
    @Log(title = "用户活动记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody UserActiveLogsBo bo) {
        if (bo.getUserId() == null) {
            bo.setUserId(LoginHelper.getUserId());
        }
        return toAjax(userActiveLogsService.insertByBo(bo));
    }

    /**
     * 修改用户活动记录
     */
    @SaCheckPermission("health:activeLogs:edit")
    @Log(title = "用户活动记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody UserActiveLogsBo bo) {
        if (bo.getUserId() == null) {
            bo.setUserId(LoginHelper.getUserId());
        }
        return toAjax(userActiveLogsService.updateByBo(bo));
    }

    /**
     * 删除用户活动记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("health:activeLogs:remove")
    @Log(title = "用户活动记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(userActiveLogsService.deleteWithValidByIds(List.of(ids), true));
    }
}
