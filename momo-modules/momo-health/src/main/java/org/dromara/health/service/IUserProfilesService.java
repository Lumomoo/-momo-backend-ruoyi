package org.dromara.health.service;

import org.dromara.health.domain.vo.UserProfilesVo;
import org.dromara.health.domain.bo.UserProfilesBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户资料详情Service接口
 *
 * @author Lumomo
 * @date 2026-01-15
 */
public interface IUserProfilesService {

    /**
     * 查询用户资料详情
     *
     * @param id 主键
     * @return 用户资料详情
     */
    UserProfilesVo queryById(Long id);

    /**
     * 分页查询用户资料详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户资料详情分页列表
     */
    TableDataInfo<UserProfilesVo> queryPageList(UserProfilesBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户资料详情列表
     *
     * @param bo 查询条件
     * @return 用户资料详情列表
     */
    List<UserProfilesVo> queryList(UserProfilesBo bo);

    /**
     * 新增用户资料详情
     *
     * @param bo 用户资料详情
     * @return 是否新增成功
     */
    Boolean insertByBo(UserProfilesBo bo);

    /**
     * 修改用户资料详情
     *
     * @param bo 用户资料详情
     * @return 是否修改成功
     */
    Boolean updateByBo(UserProfilesBo bo);

    /**
     * 校验并批量删除用户资料详情信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
