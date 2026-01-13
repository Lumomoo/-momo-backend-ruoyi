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
import org.dromara.health.domain.bo.FoodUnitBo;
import org.dromara.health.domain.vo.FoodUnitVo;
import org.dromara.health.domain.FoodUnit;
import org.dromara.health.mapper.FoodUnitMapper;
import org.dromara.health.service.IFoodUnitService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 食物单位换算Service业务层处理
 *
 * @author Lomomo
 * @date 2026-01-13
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FoodUnitServiceImpl implements IFoodUnitService {

    private final FoodUnitMapper baseMapper;

    /**
     * 查询食物单位换算
     *
     * @param id 主键
     * @return 食物单位换算
     */
    @Override
    public FoodUnitVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询食物单位换算列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 食物单位换算分页列表
     */
    @Override
    public TableDataInfo<FoodUnitVo> queryPageList(FoodUnitBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FoodUnit> lqw = buildQueryWrapper(bo);
        Page<FoodUnitVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的食物单位换算列表
     *
     * @param bo 查询条件
     * @return 食物单位换算列表
     */
    @Override
    public List<FoodUnitVo> queryList(FoodUnitBo bo) {
        LambdaQueryWrapper<FoodUnit> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FoodUnit> buildQueryWrapper(FoodUnitBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FoodUnit> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(FoodUnit::getId);
        lqw.eq(bo.getFoodId() != null, FoodUnit::getFoodId, bo.getFoodId());
        lqw.like(StringUtils.isNotBlank(bo.getUnitName()), FoodUnit::getUnitName, bo.getUnitName());
        lqw.eq(bo.getWeightGram() != null, FoodUnit::getWeightGram, bo.getWeightGram());
        return lqw;
    }

    /**
     * 新增食物单位换算
     *
     * @param bo 食物单位换算
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FoodUnitBo bo) {
        FoodUnit add = MapstructUtils.convert(bo, FoodUnit.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改食物单位换算
     *
     * @param bo 食物单位换算
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FoodUnitBo bo) {
        FoodUnit update = MapstructUtils.convert(bo, FoodUnit.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FoodUnit entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除食物单位换算信息
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
