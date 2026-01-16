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
import org.dromara.health.domain.bo.ActivityMetBo;
import org.dromara.health.domain.vo.ActivityMetVo;
import org.dromara.health.domain.ActivityMet;
import org.dromara.health.mapper.ActivityMetMapper;
import org.dromara.health.service.IActivityMetService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 运动类型MET参考Service业务层处理
 *
 * @author Lumomo
 * @date 2026-01-15
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ActivityMetServiceImpl implements IActivityMetService {

    private final ActivityMetMapper baseMapper;

    /**
     * 查询运动类型MET参考
     *
     * @param id 主键
     * @return 运动类型MET参考
     */
    @Override
    public ActivityMetVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询运动类型MET参考列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 运动类型MET参考分页列表
     */
    @Override
    public TableDataInfo<ActivityMetVo> queryPageList(ActivityMetBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ActivityMet> lqw = buildQueryWrapper(bo);
        Page<ActivityMetVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的运动类型MET参考列表
     *
     * @param bo 查询条件
     * @return 运动类型MET参考列表
     */
    @Override
    public List<ActivityMetVo> queryList(ActivityMetBo bo) {
        LambdaQueryWrapper<ActivityMet> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ActivityMet> buildQueryWrapper(ActivityMetBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ActivityMet> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(ActivityMet::getId);
        lqw.like(StringUtils.isNotBlank(bo.getActivityName()), ActivityMet::getActivityName, bo.getActivityName());
        lqw.eq(StringUtils.isNotBlank(bo.getActivityNameEn()), ActivityMet::getActivityNameEn, bo.getActivityNameEn());
        lqw.eq(StringUtils.isNotBlank(bo.getCategory()), ActivityMet::getCategory, bo.getCategory());
        lqw.eq(StringUtils.isNotBlank(bo.getSubcategory()), ActivityMet::getSubcategory, bo.getSubcategory());
        lqw.eq(bo.getMetValue() != null, ActivityMet::getMetValue, bo.getMetValue());
        return lqw;
    }

    /**
     * 新增运动类型MET参考
     *
     * @param bo 运动类型MET参考
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(ActivityMetBo bo) {
        ActivityMet add = MapstructUtils.convert(bo, ActivityMet.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改运动类型MET参考
     *
     * @param bo 运动类型MET参考
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(ActivityMetBo bo) {
        ActivityMet update = MapstructUtils.convert(bo, ActivityMet.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ActivityMet entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除运动类型MET参考信息
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
