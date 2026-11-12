package kz.kaspi.models.util;

import kz.kaspi.models.constant.KaspiPricingConstants;

import java.math.BigDecimal;

/**
 * Утилитный класс для безопасных финансовых расчетов и банковского округления (HALF_EVEN).
 */
public final class KaspiMoneyUtil {

    private KaspiMoneyUtil() {
    }

    /**
     * Округляет денежную сумму по банковскому правилу до 2 знаков после запятой (тиын/копейки).
     */
    public static BigDecimal roundMoney(BigDecimal amount) {
        if (amount == null) {
            return BigDecimal.ZERO.setScale(KaspiPricingConstants.MONEY_SCALE, KaspiPricingConstants.DEFAULT_ROUNDING_MODE);
        }
        return amount.setScale(KaspiPricingConstants.MONEY_SCALE, KaspiPricingConstants.DEFAULT_ROUNDING_MODE);
    }

    /**
     * Округляет бонусы по банковскому правилу до целого числа (1 бонус = 1 KZT).
     */
    public static BigDecimal roundBonuses(BigDecimal bonuses) {
        if (bonuses == null) {
            return BigDecimal.ZERO.setScale(KaspiPricingConstants.BONUS_SCALE, KaspiPricingConstants.DEFAULT_ROUNDING_MODE);
        }
        return bonuses.setScale(KaspiPricingConstants.BONUS_SCALE, KaspiPricingConstants.DEFAULT_ROUNDING_MODE);
    }

    /**
     * Вычисляет процентную долю от суммы (base * rate) с безопасным банковским округлением.
     */
    public static BigDecimal calculatePercentage(BigDecimal base, BigDecimal rate) {
        if (base == null || rate == null) {
            return BigDecimal.ZERO;
        }
        return roundMoney(base.multiply(rate));
    }

    /**
     * Вычисляет чистую сумму выплаты мерчанту (Net Settlement = Amount - Commission).
     */
    public static BigDecimal calculateNetPayout(BigDecimal amount, BigDecimal commission) {
        if (amount == null) {
            return roundMoney(BigDecimal.ZERO);
        }
        BigDecimal comm = commission != null ? commission : BigDecimal.ZERO;
        return roundMoney(amount.subtract(comm));
    }
}
