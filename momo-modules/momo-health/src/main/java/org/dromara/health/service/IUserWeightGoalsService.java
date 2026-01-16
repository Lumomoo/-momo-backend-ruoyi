package org.dromara.health.service;

import org.dromara.health.domain.vo.UserWeightGoalsVo;
import org.dromara.health.domain.bo.UserWeightGoalsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户减肥目标Service接口
 *
 * @author Lumomo
 * @date 2026-01-15
 */
public interface IUserWeightGoalsService {

    /**
     * 查询用户减肥目标
     *
     * @param id 主键
     * @return 用户减肥目标
     */
    UserWeightGoalsVo queryById(Long id);

    /**
     * 分页查询用户减肥目标列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户减肥目标分页列表
     */
    TableDataInfo<UserWeightGoalsVo> queryPageList(UserWeightGoalsBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户减肥目标列表
     *
     * @param bo 查询条件
     * @return 用户减肥目标列表
     */
    List<UserWeightGoalsVo> queryList(UserWeightGoalsBo bo);

    /**
     * 新增用户减肥目标
     *
     * @param bo 用户减肥目标
     * @return 是否新增成功
     */
    Boolean insertByBo(UserWeightGoalsBo bo);

    /**
     * 修改用户减肥目标
     *
     * @param bo 用户减肥目标
     * @return 是否修改成功
     */
    Boolean updateByBo(UserWeightGoalsBo bo);

    /**
     * 校验并批量删除用户减肥目标信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
