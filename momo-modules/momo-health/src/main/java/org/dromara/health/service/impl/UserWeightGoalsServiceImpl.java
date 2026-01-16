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
import org.dromara.health.domain.bo.UserWeightGoalsBo;
import org.dromara.health.domain.vo.UserWeightGoalsVo;
import org.dromara.health.domain.UserWeightGoals;
import org.dromara.health.mapper.UserWeightGoalsMapper;
import org.dromara.health.service.IUserWeightGoalsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户减肥目标Service业务层处理
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserWeightGoalsServiceImpl implements IUserWeightGoalsService {

    private final UserWeightGoalsMapper baseMapper;

    /**
     * 查询用户减肥目标
     *
     * @param id 主键
     * @return 用户减肥目标
     */
    @Override
    public UserWeightGoalsVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询用户减肥目标列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户减肥目标分页列表
     */
    @Override
    public TableDataInfo<UserWeightGoalsVo> queryPageList(UserWeightGoalsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<UserWeightGoals> lqw = buildQueryWrapper(bo);
        Page<UserWeightGoalsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户减肥目标列表
     *
     * @param bo 查询条件
     * @return 用户减肥目标列表
     */
    @Override
    public List<UserWeightGoalsVo> queryList(UserWeightGoalsBo bo) {
        LambdaQueryWrapper<UserWeightGoals> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<UserWeightGoals> buildQueryWrapper(UserWeightGoalsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<UserWeightGoals> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(UserWeightGoals::getId);
        lqw.eq(bo.getUserId() != null, UserWeightGoals::getUserId, bo.getUserId());
        lqw.eq(bo.getStartWeight() != null, UserWeightGoals::getStartWeight, bo.getStartWeight());
        lqw.eq(bo.getTargetWeight() != null, UserWeightGoals::getTargetWeight, bo.getTargetWeight());
        lqw.eq(bo.getTargetDate() != null, UserWeightGoals::getTargetDate, bo.getTargetDate());
        lqw.eq(bo.getCurrentStatus() != null, UserWeightGoals::getCurrentStatus, bo.getCurrentStatus());
        return lqw;
    }

    /**
     * 新增用户减肥目标
     *
     * @param bo 用户减肥目标
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(UserWeightGoalsBo bo) {
        UserWeightGoals add = MapstructUtils.convert(bo, UserWeightGoals.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户减肥目标
     *
     * @param bo 用户减肥目标
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(UserWeightGoalsBo bo) {
        UserWeightGoals update = MapstructUtils.convert(bo, UserWeightGoals.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(UserWeightGoals entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户减肥目标信息
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
