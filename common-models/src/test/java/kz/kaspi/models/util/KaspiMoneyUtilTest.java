package kz.kaspi.models.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование финансовых расчетов KaspiMoneyUtil")
class KaspiMoneyUtilTest {

    @Test
    @DisplayName("Округление денежных сумм по банковскому правилу (HALF_EVEN)")
    void testRoundMoney() {
        assertEquals(new BigDecimal("100.50"), KaspiMoneyUtil.roundMoney(new BigDecimal("100.504")));
        assertEquals(new BigDecimal("100.51"), KaspiMoneyUtil.roundMoney(new BigDecimal("100.506")));
        assertEquals(new BigDecimal("0.00"), KaspiMoneyUtil.roundMoney(null));
    }

    @Test
    @DisplayName("Округление бонусов до целого значения")
    void testRoundBonuses() {
        assertEquals(new BigDecimal("50"), KaspiMoneyUtil.roundBonuses(new BigDecimal("49.8")));
        assertEquals(new BigDecimal("0"), KaspiMoneyUtil.roundBonuses(null));
    }

    @Test
    @DisplayName("Корректный расчет Net Settlement (Сумма - Комиссия)")
    void testCalculateNetPayout() {
        BigDecimal amount = new BigDecimal("10000.00");
        BigDecimal commission = new BigDecimal("95.00"); // 0.95%
        BigDecimal payout = KaspiMoneyUtil.calculateNetPayout(amount, commission);
        assertEquals(new BigDecimal("9905.00"), payout);
    }

    @Test
    @DisplayName("Безопасное определение min и max")
    void testMinAndMax() {
        BigDecimal a = new BigDecimal("100");
        BigDecimal b = new BigDecimal("200");
        assertEquals(a, KaspiMoneyUtil.min(a, b));
        assertEquals(b, KaspiMoneyUtil.max(a, b));
        assertEquals(a, KaspiMoneyUtil.min(a, null));
    }

    @Test
    @DisplayName("Проверка условий положительных и нулевых сумм")
    void testIsPositiveAndZero() {
        assertTrue(KaspiMoneyUtil.isPositive(new BigDecimal("1.00")));
        assertFalse(KaspiMoneyUtil.isPositive(BigDecimal.ZERO));
        assertTrue(KaspiMoneyUtil.isZeroOrNegative(BigDecimal.ZERO));
        assertTrue(KaspiMoneyUtil.isZeroOrNegative(null));
    }
}
