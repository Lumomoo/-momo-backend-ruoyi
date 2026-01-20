package org.dromara.health.service;

import org.dromara.health.domain.vo.UserActiveLogsVo;
import org.dromara.health.domain.vo.UserActiveLogsSummaryVo;
import org.dromara.health.domain.bo.UserActiveLogsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;
import java.time.LocalDate;

/**
 * 用户活动记录Service接口
 *
 * @author Lumomo
 * @date 2026-01-15
 */
public interface IUserActiveLogsService {

    /**
     * 查询用户活动记录
     *
     * @param id 主键
     * @return 用户活动记录
     */
    UserActiveLogsVo queryById(Long id);

    /**
     * 分页查询用户活动记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户活动记录分页列表
     */
    TableDataInfo<UserActiveLogsVo> queryPageList(UserActiveLogsBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户活动记录列表
     *
     * @param bo 查询条件
     * @return 用户活动记录列表
     */
    List<UserActiveLogsVo> queryList(UserActiveLogsBo bo);

    /**
     * 查询用户指定日期的资料、健康体征与活动记录汇总
     *
     * @param userId     用户ID
     * @param recordDate 查询日期
     * @return 用户指定日期的资料、健康体征与活动记录汇总
     */
    UserActiveLogsSummaryVo querySummaryByUserIdAndDate(
            // 用户ID
            Long userId,
            // 查询日期
            LocalDate recordDate);

    /**
     * 查询用户最新一条活动记录
     *
     * @param userId 用户ID
     * @return 用户最新一条活动记录
     */
    UserActiveLogsVo queryLatestByUserId(
            // 用户ID
            Long userId);

    /**
     * 新增用户活动记录
     *
     * @param bo 用户活动记录
     * @return 是否新增成功
     */
    Boolean insertByBo(UserActiveLogsBo bo);

    /**
     * 修改用户活动记录
     *
     * @param bo 用户活动记录
     * @return 是否修改成功
     */
    Boolean updateByBo(UserActiveLogsBo bo);

    /**
     * 校验并批量删除用户活动记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
