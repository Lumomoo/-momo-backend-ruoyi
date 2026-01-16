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
import org.dromara.health.domain.vo.UserHealthStatsVo;
import org.dromara.health.domain.bo.UserHealthStatsBo;
import org.dromara.health.service.IUserHealthStatsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户健康体征历史
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/health/healthStats")
public class UserHealthStatsController extends BaseController {

    private final IUserHealthStatsService userHealthStatsService;

    /**
     * 查询用户健康体征历史列表
     */
    @SaCheckPermission("health:healthStats:list")
    @GetMapping("/list")
    public TableDataInfo<UserHealthStatsVo> list(UserHealthStatsBo bo, PageQuery pageQuery) {
        return userHealthStatsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户健康体征历史列表
     */
    @SaCheckPermission("health:healthStats:export")
    @Log(title = "用户健康体征历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserHealthStatsBo bo, HttpServletResponse response) {
        List<UserHealthStatsVo> list = userHealthStatsService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户健康体征历史", UserHealthStatsVo.class, response);
    }

    /**
     * 获取用户健康体征历史详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("health:healthStats:query")
    @GetMapping("/{id}")
    public R<UserHealthStatsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(userHealthStatsService.queryById(id));
    }

    /**
     * 新增用户健康体征历史
     */
    @SaCheckPermission("health:healthStats:add")
    @Log(title = "用户健康体征历史", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody UserHealthStatsBo bo) {
        return toAjax(userHealthStatsService.insertByBo(bo));
    }

    /**
     * 修改用户健康体征历史
     */
    @SaCheckPermission("health:healthStats:edit")
    @Log(title = "用户健康体征历史", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody UserHealthStatsBo bo) {
        return toAjax(userHealthStatsService.updateByBo(bo));
    }

    /**
     * 删除用户健康体征历史
     *
     * @param ids 主键串
     */
    @SaCheckPermission("health:healthStats:remove")
    @Log(title = "用户健康体征历史", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(userHealthStatsService.deleteWithValidByIds(List.of(ids), true));
    }
}
