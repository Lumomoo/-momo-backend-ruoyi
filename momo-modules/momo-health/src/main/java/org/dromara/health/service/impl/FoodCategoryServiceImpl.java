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
import org.dromara.health.domain.bo.FoodCategoryBo;
import org.dromara.health.domain.vo.FoodCategoryVo;
import org.dromara.health.domain.FoodCategory;
import org.dromara.health.mapper.FoodCategoryMapper;
import org.dromara.health.service.IFoodCategoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 食物分类Service业务层处理
 *
 * @author Lion Li
 * @date 2026-01-13
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FoodCategoryServiceImpl implements IFoodCategoryService {

    private final FoodCategoryMapper baseMapper;

    /**
     * 查询食物分类
     *
     * @param id 主键
     * @return 食物分类
     */
    @Override
    public FoodCategoryVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 根据分类ID查询分类名称
     *
     * @param id 分类ID
     * @return 分类名称
     */
    @Override
    public String selectCategoryNameById(Long id) {
        if (id == null) {
            return null;
        }
        FoodCategory category = baseMapper.selectById(id);
        return category == null ? null : category.getName();
    }

    /**
     * 分页查询食物分类列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 食物分类分页列表
     */
    @Override
    public TableDataInfo<FoodCategoryVo> queryPageList(FoodCategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FoodCategory> lqw = buildQueryWrapper(bo);
        Page<FoodCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的食物分类列表
     *
     * @param bo 查询条件
     * @return 食物分类列表
     */
    @Override
    public List<FoodCategoryVo> queryList(FoodCategoryBo bo) {
        LambdaQueryWrapper<FoodCategory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FoodCategory> buildQueryWrapper(FoodCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FoodCategory> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(FoodCategory::getId);
        lqw.like(StringUtils.isNotBlank(bo.getName()), FoodCategory::getName, bo.getName());
        lqw.eq(bo.getSortOrder() != null, FoodCategory::getSortOrder, bo.getSortOrder());
        return lqw;
    }

    /**
     * 新增食物分类
     *
     * @param bo 食物分类
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FoodCategoryBo bo) {
        FoodCategory add = MapstructUtils.convert(bo, FoodCategory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改食物分类
     *
     * @param bo 食物分类
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FoodCategoryBo bo) {
        FoodCategory update = MapstructUtils.convert(bo, FoodCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FoodCategory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除食物分类信息
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
