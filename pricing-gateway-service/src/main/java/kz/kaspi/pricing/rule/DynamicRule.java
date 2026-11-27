package kz.kaspi.pricing.rule;

import java.math.BigDecimal;

/**
 * Доменная модель маркетингового правила начисления бонусов/тарифов.
 *
 * @param ruleId               Идентификатор правила.
 * @param campaignCode         Код кампании (например, KASPI_JUMA, KASPI_RED_PLUS).
 * @param conditionExpression  Условие на MVEL DSL (например, "category.name() == 'ELECTRONICS' && amount >= 100000").
 * @param bonusRate            Процент начисляемых бонусов (например, 0.10 для 10%).
 * @param priority             Приоритет применения правила (чем выше число, тем выше приоритет).
 * @param active               Флаг активности правила.
 */
public record DynamicRule(
        Long ruleId,
        String campaignCode,
        String conditionExpression,
        BigDecimal bonusRate,
        int priority,
        boolean active
) {
}
