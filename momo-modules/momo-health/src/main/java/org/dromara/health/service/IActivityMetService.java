package org.dromara.health.service;

import org.dromara.health.domain.vo.ActivityMetVo;
import org.dromara.health.domain.bo.ActivityMetBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 运动类型MET参考Service接口
 *
 * @author Lumomo
 * @date 2026-01-15
 */
public interface IActivityMetService {

    /**
     * 查询运动类型MET参考
     *
     * @param id 主键
     * @return 运动类型MET参考
     */
    ActivityMetVo queryById(Long id);

    /**
     * 分页查询运动类型MET参考列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 运动类型MET参考分页列表
     */
    TableDataInfo<ActivityMetVo> queryPageList(ActivityMetBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的运动类型MET参考列表
     *
     * @param bo 查询条件
     * @return 运动类型MET参考列表
     */
    List<ActivityMetVo> queryList(ActivityMetBo bo);

    /**
     * 新增运动类型MET参考
     *
     * @param bo 运动类型MET参考
     * @return 是否新增成功
     */
    Boolean insertByBo(ActivityMetBo bo);

    /**
     * 修改运动类型MET参考
     *
     * @param bo 运动类型MET参考
     * @return 是否修改成功
     */
    Boolean updateByBo(ActivityMetBo bo);

    /**
     * 校验并批量删除运动类型MET参考信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
