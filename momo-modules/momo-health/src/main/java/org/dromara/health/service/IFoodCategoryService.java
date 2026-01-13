package org.dromara.health.service;

import org.dromara.health.domain.vo.FoodCategoryVo;
import org.dromara.health.domain.bo.FoodCategoryBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 食物分类Service接口
 *
 * @author Lion Li
 * @date 2026-01-13
 */
public interface IFoodCategoryService {

    /**
     * 查询食物分类
     *
     * @param id 主键
     * @return 食物分类
     */
    FoodCategoryVo queryById(Long id);

    /**
     * 根据分类ID查询分类名称
     *
     * @param id 分类ID
     * @return 分类名称
     */
    String selectCategoryNameById(Long id);

    /**
     * 分页查询食物分类列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 食物分类分页列表
     */
    TableDataInfo<FoodCategoryVo> queryPageList(FoodCategoryBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的食物分类列表
     *
     * @param bo 查询条件
     * @return 食物分类列表
     */
    List<FoodCategoryVo> queryList(FoodCategoryBo bo);

    /**
     * 新增食物分类
     *
     * @param bo 食物分类
     * @return 是否新增成功
     */
    Boolean insertByBo(FoodCategoryBo bo);

    /**
     * 修改食物分类
     *
     * @param bo 食物分类
     * @return 是否修改成功
     */
    Boolean updateByBo(FoodCategoryBo bo);

    /**
     * 校验并批量删除食物分类信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
