package org.dromara.health.service.impl;

import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.dromara.health.domain.bo.UserActiveLogsBo;
import org.dromara.health.domain.vo.UserActiveLogsSummaryVo;
import org.dromara.health.domain.vo.UserActiveLogsVo;
import org.dromara.health.domain.vo.UserHealthStatsVo;
import org.dromara.health.domain.vo.UserProfilesVo;
import org.dromara.health.domain.FoodInfo;
import org.dromara.health.domain.UserActiveLogs;
import org.dromara.health.domain.UserHealthStats;
import org.dromara.health.mapper.FoodInfoMapper;
import org.dromara.health.mapper.UserActiveLogsMapper;
import org.dromara.health.mapper.UserHealthStatsMapper;
import org.dromara.health.service.IUserActiveLogsService;
import org.dromara.health.service.IUserProfilesService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户活动记录Service业务层处理
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserActiveLogsServiceImpl implements IUserActiveLogsService {

    private final UserActiveLogsMapper baseMapper;
    private final IUserProfilesService userProfilesService;
    private final UserHealthStatsMapper userHealthStatsMapper;
    private final FoodInfoMapper foodInfoMapper;

    /**
     * 查询用户活动记录
     *
     * @param id 主键
     * @return 用户活动记录
     */
    @Override
    public UserActiveLogsVo queryById(Long id){
        UserActiveLogsVo vo = baseMapper.selectVoById(id);
        fillFoodNutrition(vo);
        return vo;
    }

    /**
     * 分页查询用户活动记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户活动记录分页列表
     */
    @Override
    public TableDataInfo<UserActiveLogsVo> queryPageList(UserActiveLogsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<UserActiveLogs> lqw = buildQueryWrapper(bo);
        Page<UserActiveLogsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        fillFoodNutrition(result.getRecords());
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户活动记录列表
     *
     * @param bo 查询条件
     * @return 用户活动记录列表
     */
    @Override
    public List<UserActiveLogsVo> queryList(UserActiveLogsBo bo) {
        LambdaQueryWrapper<UserActiveLogs> lqw = buildQueryWrapper(bo);
        List<UserActiveLogsVo> list = baseMapper.selectVoList(lqw);
        fillFoodNutrition(list);
        return list;
    }

    /**
     * 查询用户最新一条活动记录
     *
     * @param userId 用户ID
     * @return 用户最新一条活动记录
     */
    @Override
    public UserActiveLogsVo queryLatestByUserId(
            // 用户ID
            Long userId) {
        LambdaQueryWrapper<UserActiveLogs> lqw = Wrappers.lambdaQuery();
        lqw.eq(UserActiveLogs::getUserId, userId);
        lqw.orderByDesc(UserActiveLogs::getCreateTime);
        lqw.orderByDesc(UserActiveLogs::getId);
        lqw.last("limit 1");
        UserActiveLogsVo vo = baseMapper.selectVoOne(lqw, false);
        fillFoodNutrition(vo);
        return vo;
    }

    /**
     * 查询用户指定日期的资料、健康体征与活动记录汇总
     *
     * @param userId     用户ID
     * @param recordDate 查询日期
     * @return 用户指定日期的资料、健康体征与活动记录汇总
     */
    @Override
    public UserActiveLogsSummaryVo querySummaryByUserIdAndDate(
            // 用户ID
            Long userId,
            // 查询日期
            LocalDate recordDate) {
        UserProfilesVo userProfiles = userProfilesService.queryByUserId(userId);
        List<UserActiveLogsVo> userActiveLogs = queryUserActiveLogsByDate(userId, recordDate);
        UserActiveLogsSummaryVo summary = new UserActiveLogsSummaryVo();
        summary.setUserProfiles(userProfiles);
        summary.setUserActiveLogs(userActiveLogs);
        return summary;
    }

    /**
     * 查询用户指定日期的活动记录列表
     *
     * @param userId     用户ID
     * @param recordDate 查询日期
     * @return 用户指定日期的活动记录列表
     */
    private List<UserActiveLogsVo> queryUserActiveLogsByDate(
            // 用户ID
            Long userId,
            // 查询日期
            LocalDate recordDate) {
        Date startDate = DateUtils.toDate(recordDate);
        Date endDate = DateUtils.toDate(recordDate.plusDays(1));
        LambdaQueryWrapper<UserActiveLogs> lqw = Wrappers.lambdaQuery();
        lqw.eq(UserActiveLogs::getUserId, userId);
        lqw.ge(UserActiveLogs::getCreateTime, startDate);
        lqw.lt(UserActiveLogs::getCreateTime, endDate);
        lqw.orderByAsc(UserActiveLogs::getId);
        List<UserActiveLogsVo> list = baseMapper.selectVoList(lqw);
        List<UserActiveLogsVo> result = list == null ? new ArrayList<>() : list;
        fillFoodNutrition(result);
        return result;
    }

    /**
     * 填充饮食记录的营养素信息
     *
     * @param list 活动记录列表
     */
    private void fillFoodNutrition(
            // 活动记录列表
            List<UserActiveLogsVo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> foodIds = list.stream()
                .filter(Objects::nonNull)
                .map(UserActiveLogsVo::getFoodId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (foodIds.isEmpty()) {
            return;
        }
        Map<Long, FoodInfo> foodInfoMap = foodInfoMapper.selectBatchIds(foodIds).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(FoodInfo::getId, item -> item, (left, right) -> left));
        for (UserActiveLogsVo item : list) {
            if (item == null || item.getFoodId() == null || item.getFoodAmount() == null) {
                continue;
            }
            FoodInfo foodInfo = foodInfoMap.get(item.getFoodId());
            if (foodInfo == null) {
                continue;
            }
            Double scale = item.getFoodAmount() / 100D;
            item.setProtein(calcByScale(foodInfo.getProtein(), scale));
            item.setFat(calcByScale(foodInfo.getFat(), scale));
            item.setCarbohydrate(calcByScale(foodInfo.getCarbohydrate(), scale));
            item.setFiber(calcByScale(foodInfo.getFiber(), scale));
        }
    }

    /**
     * 填充单条饮食记录的营养素信息
     *
     * @param vo 活动记录
     */
    private void fillFoodNutrition(
            // 活动记录
            UserActiveLogsVo vo) {
        if (vo == null) {
            return;
        }
        List<UserActiveLogsVo> list = new ArrayList<>();
        list.add(vo);
        fillFoodNutrition(list);
    }

    /**
     * 计算按分量折算后的营养素
     *
     * @param value 营养素含量
     * @param scale 折算比例
     * @return 折算后的营养素含量
     */
    private Double calcByScale(
            // 营养素含量
            Double value,
            // 折算比例
            Double scale) {
        if (value == null || scale == null) {
            return null;
        }
        return value * scale;
    }

    /**
     * 查询用户指定日期或最近日期的健康体征
     *
     * @param userId       用户ID
     * @param recordDate   查询日期
     * @param userProfiles 用户资料
     * @return 用户指定日期或最近日期的健康体征
     */
    private UserHealthStatsVo queryNearestHealthStats(
            // 用户ID
            Long userId,
            // 查询日期
            LocalDate recordDate,
            // 用户资料
            UserProfilesVo userProfiles) {
        UserHealthStatsVo sameDayStats = queryLatestHealthStatsByDate(userId, recordDate);
        if (sameDayStats != null) {
            return sameDayStats;
        }
        Date targetDate = DateUtils.toDate(recordDate);
        Date endDate = DateUtils.toDate(recordDate.plusDays(1));
        UserHealthStatsVo beforeStats = queryLatestHealthStatsBeforeDate(userId, targetDate);
        UserHealthStatsVo afterStats = queryEarliestHealthStatsAfterDate(userId, endDate);
        if (beforeStats == null && afterStats == null) {
            return buildDefaultHealthStats(userId, recordDate, userProfiles);
        }
        if (beforeStats == null) {
            return afterStats;
        }
        if (afterStats == null) {
            return beforeStats;
        }
        long beforeDiff = Math.abs(targetDate.getTime() - beforeStats.getRecordDate().getTime());
        long afterDiff = Math.abs(afterStats.getRecordDate().getTime() - targetDate.getTime());
        return beforeDiff <= afterDiff ? beforeStats : afterStats;
    }

    /**
     * 查询用户指定日期最新一条健康体征
     *
     * @param userId     用户ID
     * @param recordDate 查询日期
     * @return 用户指定日期最新一条健康体征
     */
    private UserHealthStatsVo queryLatestHealthStatsByDate(
            // 用户ID
            Long userId,
            // 查询日期
            LocalDate recordDate) {
        Date startDate = DateUtils.toDate(recordDate);
        Date endDate = DateUtils.toDate(recordDate.plusDays(1));
        LambdaQueryWrapper<UserHealthStats> lqw = Wrappers.lambdaQuery();
        lqw.eq(UserHealthStats::getUserId, userId);
        lqw.isNotNull(UserHealthStats::getRecordDate);
        lqw.ge(UserHealthStats::getRecordDate, startDate);
        lqw.lt(UserHealthStats::getRecordDate, endDate);
        lqw.orderByDesc(UserHealthStats::getRecordDate);
        lqw.orderByDesc(UserHealthStats::getCreateTime);
        lqw.orderByDesc(UserHealthStats::getId);
        lqw.last("limit 1");
        return userHealthStatsMapper.selectVoOne(lqw, false);
    }

    /**
     * 查询用户指定日期之前最新一条健康体征
     *
     * @param userId     用户ID
     * @param targetDate 目标日期
     * @return 用户指定日期之前最新一条健康体征
     */
    private UserHealthStatsVo queryLatestHealthStatsBeforeDate(
            // 用户ID
            Long userId,
            // 目标日期
            Date targetDate) {
        LambdaQueryWrapper<UserHealthStats> lqw = Wrappers.lambdaQuery();
        lqw.eq(UserHealthStats::getUserId, userId);
        lqw.isNotNull(UserHealthStats::getRecordDate);
        lqw.lt(UserHealthStats::getRecordDate, targetDate);
        lqw.orderByDesc(UserHealthStats::getRecordDate);
        lqw.orderByDesc(UserHealthStats::getCreateTime);
        lqw.orderByDesc(UserHealthStats::getId);
        lqw.last("limit 1");
        return userHealthStatsMapper.selectVoOne(lqw, false);
    }

    /**
     * 查询用户指定日期之后最早一条健康体征
     *
     * @param userId  用户ID
     * @param endDate 日期结束时间
     * @return 用户指定日期之后最早一条健康体征
     */
    private UserHealthStatsVo queryEarliestHealthStatsAfterDate(
            // 用户ID
            Long userId,
            // 日期结束时间
            Date endDate) {
        LambdaQueryWrapper<UserHealthStats> lqw = Wrappers.lambdaQuery();
        lqw.eq(UserHealthStats::getUserId, userId);
        lqw.isNotNull(UserHealthStats::getRecordDate);
        lqw.ge(UserHealthStats::getRecordDate, endDate);
        lqw.orderByAsc(UserHealthStats::getRecordDate);
        lqw.orderByAsc(UserHealthStats::getCreateTime);
        lqw.orderByAsc(UserHealthStats::getId);
        lqw.last("limit 1");
        return userHealthStatsMapper.selectVoOne(lqw, false);
    }

    /**
     * 构建默认健康体征数据
     *
     * @param userId       用户ID
     * @param recordDate   查询日期
     * @param userProfiles 用户资料
     * @return 默认健康体征数据
     */
    private UserHealthStatsVo buildDefaultHealthStats(
            // 用户ID
            Long userId,
            // 查询日期
            LocalDate recordDate,
            // 用户资料
            UserProfilesVo userProfiles) {
        UserHealthStatsVo statsVo = new UserHealthStatsVo();
        statsVo.setUserId(userId);
        statsVo.setRecordDate(DateUtils.toDate(recordDate));
        Long gender = userProfiles == null ? null : userProfiles.getGender();
        if (Long.valueOf(2L).equals(gender)) {
            statsVo.setHeight(160D);
            statsVo.setWeight(50D);
        } else {
            statsVo.setHeight(175D);
            statsVo.setWeight(60D);
        }
        return statsVo;
    }

    private LambdaQueryWrapper<UserActiveLogs> buildQueryWrapper(UserActiveLogsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<UserActiveLogs> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(UserActiveLogs::getId);
        lqw.eq(bo.getUserId() != null, UserActiveLogs::getUserId, bo.getUserId());
        lqw.eq(bo.getFoodId() != null, UserActiveLogs::getFoodId, bo.getFoodId());
        lqw.eq(bo.getExerciseId() != null, UserActiveLogs::getExerciseId, bo.getExerciseId());
        lqw.eq(bo.getActiveType() != null, UserActiveLogs::getActiveType, bo.getActiveType());
        lqw.eq(bo.getMealType() != null, UserActiveLogs::getMealType, bo.getMealType());
        lqw.eq(bo.getFoodAmount() != null, UserActiveLogs::getFoodAmount, bo.getFoodAmount());
        lqw.eq(bo.getExerciseAmount() != null, UserActiveLogs::getExerciseAmount, bo.getExerciseAmount());
        lqw.eq(bo.getTotalCalories() != null, UserActiveLogs::getTotalCalories, bo.getTotalCalories());
        return lqw;
    }

    /**
     * 新增用户活动记录
     *
     * @param bo 用户活动记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(UserActiveLogsBo bo) {
        UserActiveLogs add = MapstructUtils.convert(bo, UserActiveLogs.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户活动记录
     *
     * @param bo 用户活动记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(UserActiveLogsBo bo) {
        UserActiveLogs update = MapstructUtils.convert(bo, UserActiveLogs.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(UserActiveLogs entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户活动记录信息
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
