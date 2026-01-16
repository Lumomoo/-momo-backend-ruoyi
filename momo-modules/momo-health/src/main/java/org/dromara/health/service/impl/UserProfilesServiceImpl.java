package org.dromara.health.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.service.UserService;
import org.dromara.common.tenant.helper.TenantHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.dromara.health.domain.bo.UserProfilesBo;
import org.dromara.health.domain.vo.UserProfilesVo;
import org.dromara.health.domain.UserProfiles;
import org.dromara.health.domain.UserHealthStats;
import org.dromara.health.domain.UserWeightGoals;
import org.dromara.health.domain.vo.UserHealthStatsVo;
import org.dromara.health.domain.vo.UserWeightGoalsVo;
import org.dromara.health.mapper.UserHealthStatsMapper;
import org.dromara.health.mapper.UserProfilesMapper;
import org.dromara.health.mapper.UserWeightGoalsMapper;
import org.dromara.health.service.IUserProfilesService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 用户资料详情Service业务层处理
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserProfilesServiceImpl implements IUserProfilesService {

    private final UserProfilesMapper baseMapper;
    private final UserWeightGoalsMapper userWeightGoalsMapper;
    private final UserHealthStatsMapper userHealthStatsMapper;
    private final UserService userService;

    /**
     * 查询用户资料详情
     *
     * @param id 主键
     * @return 用户资料详情
     */
    @Override
    public UserProfilesVo queryById(Long id){
        UserProfilesVo vo = baseMapper.selectVoById(id);
        fillUserName(vo);
        fillLatestRelatedData(vo);
        return vo;
    }

    /**
     * 分页查询用户资料详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户资料详情分页列表
     */
    @Override
    public TableDataInfo<UserProfilesVo> queryPageList(UserProfilesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<UserProfiles> lqw = buildQueryWrapper(bo);
        Page<UserProfilesVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        fillLatestRelatedData(result.getRecords());
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户资料详情列表
     *
     * @param bo 查询条件
     * @return 用户资料详情列表
     */
    @Override
    public List<UserProfilesVo> queryList(UserProfilesBo bo) {
        LambdaQueryWrapper<UserProfiles> lqw = buildQueryWrapper(bo);
        List<UserProfilesVo> list = baseMapper.selectVoList(lqw);
        fillUserNames(list);
        fillLatestRelatedData(list);
        return list;
    }

    /**
     * 填充用户资料关联的用户账号
     *
     * @param vo 用户资料视图对象
     */
    private void fillUserName(
            // 用户资料视图对象
            UserProfilesVo vo) {
        if (vo == null || vo.getUserId() == null) {
            return;
        }
        String userName = TenantHelper.ignore(() -> userService.selectUserNameById(vo.getUserId()));
        vo.setUserName(userName);
    }

    /**
     * 批量填充用户资料关联的用户账号
     *
     * @param list 用户资料视图列表
     */
    private void fillUserNames(
            // 用户资料视图列表
            List<UserProfilesVo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> userIds = list.stream()
            .map(UserProfilesVo::getUserId)
            .filter(item -> item != null)
            .distinct()
            .collect(Collectors.toList());
        if (userIds.isEmpty()) {
            return;
        }
        Map<Long, String> userNameMap = TenantHelper.ignore(() -> userService.selectUserNamesByIds(userIds));
        if (userNameMap == null || userNameMap.isEmpty()) {
            return;
        }
        for (UserProfilesVo vo : list) {
            if (vo == null || vo.getUserId() == null) {
                continue;
            }
            vo.setUserName(userNameMap.get(vo.getUserId()));
        }
    }

    /**
     * 填充用户资料关联的最新体重目标与健康体征
     *
     * @param vo 用户资料视图对象
     */
    private void fillLatestRelatedData(
            // 用户资料视图对象
            UserProfilesVo vo) {
        if (vo == null || vo.getUserId() == null) {
            return;
        }
        LambdaQueryWrapper<UserWeightGoals> weightWrapper = Wrappers.lambdaQuery();
        weightWrapper.eq(UserWeightGoals::getUserId, vo.getUserId());
        weightWrapper.orderByDesc(UserWeightGoals::getCreateTime);
        weightWrapper.orderByDesc(UserWeightGoals::getId);
        weightWrapper.last("limit 1");
        UserWeightGoalsVo weightGoalsVo = userWeightGoalsMapper.selectVoOne(weightWrapper, false);
        vo.setUserWeightGoals(weightGoalsVo);

        LambdaQueryWrapper<UserHealthStats> statsWrapper = Wrappers.lambdaQuery();
        statsWrapper.eq(UserHealthStats::getUserId, vo.getUserId());
        statsWrapper.orderByDesc(UserHealthStats::getRecordDate);
        statsWrapper.orderByDesc(UserHealthStats::getCreateTime);
        statsWrapper.orderByDesc(UserHealthStats::getId);
        statsWrapper.last("limit 1");
        UserHealthStatsVo healthStatsVo = userHealthStatsMapper.selectVoOne(statsWrapper, false);
        vo.setUserHealthStats(healthStatsVo);
    }

    /**
     * 批量填充用户资料关联的最新体重目标与健康体征
     *
     * @param list 用户资料视图列表
     */
    private void fillLatestRelatedData(
            // 用户资料视图列表
            List<UserProfilesVo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (UserProfilesVo vo : list) {
            fillLatestRelatedData(vo);
        }
    }

    private LambdaQueryWrapper<UserProfiles> buildQueryWrapper(UserProfilesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<UserProfiles> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(UserProfiles::getId);
        lqw.eq(bo.getUserId() != null, UserProfiles::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getNickname()), UserProfiles::getNickname, bo.getNickname());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatarUrl()), UserProfiles::getAvatarUrl, bo.getAvatarUrl());
        lqw.eq(bo.getGender() != null, UserProfiles::getGender, bo.getGender());
        lqw.eq(bo.getBirthday() != null, UserProfiles::getBirthday, bo.getBirthday());
        lqw.eq(StringUtils.isNotBlank(bo.getRegion()), UserProfiles::getRegion, bo.getRegion());
        lqw.eq(StringUtils.isNotBlank(bo.getBio()), UserProfiles::getBio, bo.getBio());
        return lqw;
    }

    /**
     * 新增用户资料详情
     *
     * @param bo 用户资料详情
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(UserProfilesBo bo) {
        UserProfiles add = MapstructUtils.convert(bo, UserProfiles.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户资料详情
     *
     * @param bo 用户资料详情
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(UserProfilesBo bo) {
        UserProfiles update = MapstructUtils.convert(bo, UserProfiles.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(UserProfiles entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户资料详情信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
