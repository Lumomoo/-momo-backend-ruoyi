package org.dromara.health.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.dromara.health.domain.bo.UserActiveLogsBo;
import org.dromara.health.domain.vo.UserActiveLogsVo;
import org.dromara.health.domain.UserActiveLogs;
import org.dromara.health.mapper.UserActiveLogsMapper;
import org.dromara.health.service.IUserActiveLogsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

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

    /**
     * 查询用户活动记录
     *
     * @param id 主键
     * @return 用户活动记录
     */
    @Override
    public UserActiveLogsVo queryById(Long id){
        return baseMapper.selectVoById(id);
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
        return baseMapper.selectVoList(lqw);
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
