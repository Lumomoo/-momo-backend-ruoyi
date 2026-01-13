package org.dromara.health.service;

import org.dromara.health.domain.vo.FoodUnitVo;
import org.dromara.health.domain.bo.FoodUnitBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 食物单位换算Service接口
 *
 * @author Lomomo
 * @date 2026-01-13
 */
public interface IFoodUnitService {

    /**
     * 查询食物单位换算
     *
     * @param id 主键
     * @return 食物单位换算
     */
    FoodUnitVo queryById(Long id);

    /**
     * 分页查询食物单位换算列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 食物单位换算分页列表
     */
    TableDataInfo<FoodUnitVo> queryPageList(FoodUnitBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的食物单位换算列表
     *
     * @param bo 查询条件
     * @return 食物单位换算列表
     */
    List<FoodUnitVo> queryList(FoodUnitBo bo);

    /**
     * 新增食物单位换算
     *
     * @param bo 食物单位换算
     * @return 是否新增成功
     */
    Boolean insertByBo(FoodUnitBo bo);

    /**
     * 修改食物单位换算
     *
     * @param bo 食物单位换算
     * @return 是否修改成功
     */
    Boolean updateByBo(FoodUnitBo bo);

    /**
     * 校验并批量删除食物单位换算信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
