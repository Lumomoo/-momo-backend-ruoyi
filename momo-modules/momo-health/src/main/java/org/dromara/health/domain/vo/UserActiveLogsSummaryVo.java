package org.dromara.health.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户活动记录汇总视图对象
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Data
public class UserActiveLogsSummaryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户资料
     */
    private UserProfilesVo userProfiles;

    /**
     * 用户活动记录列表
     */
    private List<UserActiveLogsVo> userActiveLogs;
}
