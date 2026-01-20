package org.dromara.health.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.dromara.health.domain.bo.UserHealthStatsBo;
import org.dromara.health.domain.vo.UserHealthStatsVo;
import org.dromara.health.domain.UserHealthStats;
import org.dromara.health.mapper.UserHealthStatsMapper;
import org.dromara.health.service.IUserHealthStatsService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * 用户健康体征历史Service业务层处理
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserHealthStatsServiceImpl implements IUserHealthStatsService {

    private final UserHealthStatsMapper baseMapper;

    /**
     * 查询用户健康体征历史
     *
     * @param id 主键
     * @return 用户健康体征历史
     */
    @Override
    public UserHealthStatsVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询用户健康体征历史列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户健康体征历史分页列表
     */
    @Override
    public TableDataInfo<UserHealthStatsVo> queryPageList(UserHealthStatsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<UserHealthStats> lqw = buildQueryWrapper(bo);
        Page<UserHealthStatsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户健康体征历史列表
     *
     * @param bo 查询条件
     * @return 用户健康体征历史列表
     */
    @Override
    public List<UserHealthStatsVo> queryList(UserHealthStatsBo bo) {
        LambdaQueryWrapper<UserHealthStats> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 查询用户近七天每日最新的健康体征
     *
     * @param userId 用户ID
     * @return 用户近七天每日最新的健康体征列表
     */
    @Override
    public List<UserHealthStatsVo> queryLatestSevenDaysByUserId(
            // 用户ID
            Long userId) {
        if (userId == null) {
            return new ArrayList<>();
        }
        LocalDate today = LocalDate.now();
        Date startDate = DateUtils.toDate(today.minusDays(6));
        Date endDate = DateUtils.toDate(today.plusDays(1));
        LambdaQueryWrapper<UserHealthStats> lqw = Wrappers.lambdaQuery();
        lqw.eq(UserHealthStats::getUserId, userId);
        lqw.ge(UserHealthStats::getRecordDate, startDate);
        lqw.lt(UserHealthStats::getRecordDate, endDate);
        lqw.orderByDesc(UserHealthStats::getRecordDate);
        lqw.orderByDesc(UserHealthStats::getCreateTime);
        lqw.orderByDesc(UserHealthStats::getId);
        List<UserHealthStatsVo> list = baseMapper.selectVoList(lqw);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<LocalDate, UserHealthStatsVo> latestMap = new LinkedHashMap<>();
        for (UserHealthStatsVo vo : list) {
            if (vo == null || vo.getRecordDate() == null) {
                continue;
            }
            LocalDate recordDate = vo.getRecordDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            if (!latestMap.containsKey(recordDate)) {
                latestMap.put(recordDate, vo);
            }
        }
        return new ArrayList<>(latestMap.values());
    }

    private LambdaQueryWrapper<UserHealthStats> buildQueryWrapper(UserHealthStatsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<UserHealthStats> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(UserHealthStats::getId);
        lqw.eq(bo.getUserId() != null, UserHealthStats::getUserId, bo.getUserId());
        lqw.eq(bo.getHeight() != null, UserHealthStats::getHeight, bo.getHeight());
        lqw.eq(bo.getWeight() != null, UserHealthStats::getWeight, bo.getWeight());
        lqw.eq(StringUtils.isNotBlank(bo.getBloodType()), UserHealthStats::getBloodType, bo.getBloodType());
        lqw.eq(bo.getRecordDate() != null, UserHealthStats::getRecordDate, bo.getRecordDate());
        return lqw;
    }

    /**
     * 新增用户健康体征历史
     *
     * @param bo 用户健康体征历史
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(UserHealthStatsBo bo) {
        if (bo != null && bo.getUserId() != null
            && (bo.getHeight() == null || StringUtils.isBlank(bo.getBloodType()))) {
            LambdaQueryWrapper<UserHealthStats> lqw = Wrappers.lambdaQuery();
            lqw.eq(UserHealthStats::getUserId, bo.getUserId());
            lqw.orderByDesc(UserHealthStats::getRecordDate);
            lqw.orderByDesc(UserHealthStats::getCreateTime);
            lqw.orderByDesc(UserHealthStats::getId);
            lqw.last("limit 1");
            UserHealthStats latest = baseMapper.selectOne(lqw);
            if (latest != null) {
                if (bo.getHeight() == null && latest.getHeight() != null) {
                    bo.setHeight(latest.getHeight());
                }
                if (StringUtils.isBlank(bo.getBloodType()) && StringUtils.isNotBlank(latest.getBloodType())) {
                    bo.setBloodType(latest.getBloodType());
                }
            }
        }
        UserHealthStats add = MapstructUtils.convert(bo, UserHealthStats.class);
        add.setRecordDate(new Date());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户健康体征历史
     *
     * @param bo 用户健康体征历史
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(UserHealthStatsBo bo) {
        UserHealthStats update = MapstructUtils.convert(bo, UserHealthStats.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(UserHealthStats entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户健康体征历史信息
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
