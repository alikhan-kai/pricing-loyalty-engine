package kz.kaspi.models.dto;

import java.math.BigDecimal;

/**
 * Детализация разбиения суммы при превышении месячного лимита бонусов.
 *
 * @param eligibleAmount   Часть суммы платежа, на которую начислен повышенный бонус.
 * @param excessAmount     Остаток суммы платежа сверх лимита, рассчитанный по ставке 0%.
 * @param eligibleBonuses  Фактически начисленное количество бонусов за eligibleAmount.
 */
public record SplitDetail(
        BigDecimal eligibleAmount,
        BigDecimal excessAmount,
        BigDecimal eligibleBonuses
) {
    public static SplitDetail noSplit(BigDecimal fullAmount, BigDecimal bonuses) {
        return new SplitDetail(fullAmount, BigDecimal.ZERO, bonuses);
    }

    public static SplitDetail fullyExceeded(BigDecimal fullAmount) {
        return new SplitDetail(BigDecimal.ZERO, fullAmount, BigDecimal.ZERO);
    }
}
