package org.dromara.health.service;

import org.dromara.health.domain.vo.UserHealthStatsVo;
import org.dromara.health.domain.bo.UserHealthStatsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户健康体征历史Service接口
 *
 * @author Lumomo
 * @date 2026-01-15
 */
public interface IUserHealthStatsService {

    /**
     * 查询用户健康体征历史
     *
     * @param id 主键
     * @return 用户健康体征历史
     */
    UserHealthStatsVo queryById(Long id);

    /**
     * 分页查询用户健康体征历史列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户健康体征历史分页列表
     */
    TableDataInfo<UserHealthStatsVo> queryPageList(UserHealthStatsBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户健康体征历史列表
     *
     * @param bo 查询条件
     * @return 用户健康体征历史列表
     */
    List<UserHealthStatsVo> queryList(UserHealthStatsBo bo);

    /**
     * 新增用户健康体征历史
     *
     * @param bo 用户健康体征历史
     * @return 是否新增成功
     */
    Boolean insertByBo(UserHealthStatsBo bo);

    /**
     * 修改用户健康体征历史
     *
     * @param bo 用户健康体征历史
     * @return 是否修改成功
     */
    Boolean updateByBo(UserHealthStatsBo bo);

    /**
     * 校验并批量删除用户健康体征历史信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
