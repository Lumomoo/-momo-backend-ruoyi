package org.dromara.health.service.impl;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import org.dromara.common.translation.annotation.TranslationType;
import org.dromara.common.translation.core.TranslationInterface;
import org.dromara.health.common.constant.HealthConstant;
import org.dromara.health.service.IFoodInfoService;
import org.springframework.stereotype.Service;

/**
 * 食物名称翻译实现
 */
@RequiredArgsConstructor
@Service
@TranslationType(type = HealthConstant.FOOD_ID_TO_NAME)
public class FoodNameTranslationImpl implements TranslationInterface<String> {

    private final IFoodInfoService foodInfoService;

    @Override
    public String translation(Object key, String other) {
        return foodInfoService.selectFoodNameById(Convert.toLong(key));
    }
}
