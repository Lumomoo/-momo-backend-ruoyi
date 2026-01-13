package org.dromara.health.service;

import org.dromara.health.domain.vo.FoodInfoVo;
import org.dromara.health.domain.bo.FoodInfoBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 食物营养成分Service接口
 *
 * @author Lomomo
 * @date 2026-01-13
 */
public interface IFoodInfoService {

    /**
     * 查询食物营养成分
     *
     * @param id 主键
     * @return 食物营养成分
     */
    FoodInfoVo queryById(Long id);

    /**
     * 分页查询食物营养成分列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 食物营养成分分页列表
     */
    TableDataInfo<FoodInfoVo> queryPageList(FoodInfoBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的食物营养成分列表
     *
     * @param bo 查询条件
     * @return 食物营养成分列表
     */
    List<FoodInfoVo> queryList(FoodInfoBo bo);

    /**
     * 新增食物营养成分
     *
     * @param bo 食物营养成分
     * @return 是否新增成功
     */
    Boolean insertByBo(FoodInfoBo bo);

    /**
     * 修改食物营养成分
     *
     * @param bo 食物营养成分
     * @return 是否修改成功
     */
    Boolean updateByBo(FoodInfoBo bo);

    /**
     * 校验并批量删除食物营养成分信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
