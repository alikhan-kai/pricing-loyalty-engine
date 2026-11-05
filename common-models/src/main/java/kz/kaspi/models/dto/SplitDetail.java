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

    /**
     * Возвращает признак того, что сумма была разделена на акционную и безакционную части.
     */
    public boolean isSplit() {
        return excessAmount != null && excessAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Возвращает общую сумму транзакции (eligibleAmount + excessAmount).
     */
    public BigDecimal totalAmount() {
        BigDecimal eligible = eligibleAmount != null ? eligibleAmount : BigDecimal.ZERO;
        BigDecimal excess = excessAmount != null ? excessAmount : BigDecimal.ZERO;
        return eligible.add(excess);
    }
}
