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
import org.dromara.health.domain.bo.UserHealthStatsBo;
import org.dromara.health.domain.vo.UserHealthStatsVo;
import org.dromara.health.domain.UserHealthStats;
import org.dromara.health.mapper.UserHealthStatsMapper;
import org.dromara.health.service.IUserHealthStatsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

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
        UserHealthStats add = MapstructUtils.convert(bo, UserHealthStats.class);
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
