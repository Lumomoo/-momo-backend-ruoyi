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
import org.dromara.health.domain.bo.FoodInfoBo;
import org.dromara.health.domain.vo.FoodInfoVo;
import org.dromara.health.domain.FoodInfo;
import org.dromara.health.mapper.FoodInfoMapper;
import org.dromara.health.service.IFoodInfoService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 食物营养成分Service业务层处理
 *
 * @author Lomomo
 * @date 2026-01-13
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FoodInfoServiceImpl implements IFoodInfoService {

    private final FoodInfoMapper baseMapper;

    /**
     * 查询食物营养成分
     *
     * @param id 主键
     * @return 食物营养成分
     */
    @Override
    public FoodInfoVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询食物营养成分列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 食物营养成分分页列表
     */
    @Override
    public TableDataInfo<FoodInfoVo> queryPageList(FoodInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FoodInfo> lqw = buildQueryWrapper(bo);
        Page<FoodInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的食物营养成分列表
     *
     * @param bo 查询条件
     * @return 食物营养成分列表
     */
    @Override
    public List<FoodInfoVo> queryList(FoodInfoBo bo) {
        LambdaQueryWrapper<FoodInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FoodInfo> buildQueryWrapper(FoodInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FoodInfo> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(FoodInfo::getId);
        lqw.eq(bo.getCategoryId() != null, FoodInfo::getCategoryId, bo.getCategoryId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), FoodInfo::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getBrand()), FoodInfo::getBrand, bo.getBrand());
        lqw.eq(bo.getCalories() != null, FoodInfo::getCalories, bo.getCalories());
        lqw.eq(bo.getProtein() != null, FoodInfo::getProtein, bo.getProtein());
        lqw.eq(bo.getFat() != null, FoodInfo::getFat, bo.getFat());
        lqw.eq(bo.getCarbohydrate() != null, FoodInfo::getCarbohydrate, bo.getCarbohydrate());
        lqw.eq(bo.getFiber() != null, FoodInfo::getFiber, bo.getFiber());
        return lqw;
    }

    /**
     * 新增食物营养成分
     *
     * @param bo 食物营养成分
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FoodInfoBo bo) {
        FoodInfo add = MapstructUtils.convert(bo, FoodInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改食物营养成分
     *
     * @param bo 食物营养成分
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FoodInfoBo bo) {
        FoodInfo update = MapstructUtils.convert(bo, FoodInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FoodInfo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除食物营养成分信息
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
