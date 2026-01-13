package org.dromara.health.service.impl;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import org.dromara.common.translation.annotation.TranslationType;
import org.dromara.common.translation.core.TranslationInterface;
import org.dromara.health.common.constant.HealthConstant;
import org.dromara.health.service.IFoodCategoryService;
import org.springframework.stereotype.Service;

/**
 * 食物分类名称翻译实现
 */
@RequiredArgsConstructor
@Service
@TranslationType(type = HealthConstant.CATEGORY_ID_TO_NAME)
public class FoodCategoryNameTranslationImpl implements TranslationInterface<String> {

    private final IFoodCategoryService foodCategoryService;

    @Override
    public String translation(Object key, String other) {
        return foodCategoryService.selectCategoryNameById(Convert.toLong(key));
    }
}
