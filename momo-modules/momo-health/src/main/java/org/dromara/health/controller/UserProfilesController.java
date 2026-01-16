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
import org.dromara.health.domain.vo.UserProfilesVo;
import org.dromara.health.domain.bo.UserProfilesBo;
import org.dromara.health.service.IUserProfilesService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户资料详情
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/health/profiles")
public class UserProfilesController extends BaseController {

    private final IUserProfilesService userProfilesService;

    /**
     * 查询用户资料详情列表
     */
    @SaCheckPermission("health:profiles:list")
    @GetMapping("/list")
    public TableDataInfo<UserProfilesVo> list(UserProfilesBo bo, PageQuery pageQuery) {
        return userProfilesService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户资料详情列表
     */
    @SaCheckPermission("health:profiles:export")
    @Log(title = "用户资料详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserProfilesBo bo, HttpServletResponse response) {
        List<UserProfilesVo> list = userProfilesService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户资料详情", UserProfilesVo.class, response);
    }

    /**
     * 获取用户资料详情详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("health:profiles:query")
    @GetMapping("/{id}")
    public R<UserProfilesVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(userProfilesService.queryById(id));
    }

    /**
     * 新增用户资料详情
     */
    @SaCheckPermission("health:profiles:add")
    @Log(title = "用户资料详情", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody UserProfilesBo bo) {
        return toAjax(userProfilesService.insertByBo(bo));
    }

    /**
     * 修改用户资料详情
     */
    @SaCheckPermission("health:profiles:edit")
    @Log(title = "用户资料详情", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody UserProfilesBo bo) {
        return toAjax(userProfilesService.updateByBo(bo));
    }

    /**
     * 删除用户资料详情
     *
     * @param ids 主键串
     */
    @SaCheckPermission("health:profiles:remove")
    @Log(title = "用户资料详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(userProfilesService.deleteWithValidByIds(List.of(ids), true));
    }
}
