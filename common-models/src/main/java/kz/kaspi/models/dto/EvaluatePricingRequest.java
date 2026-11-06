package kz.kaspi.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kz.kaspi.models.enums.MerchantCategory;
import kz.kaspi.models.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Входящий контракт на предрасчет комиссии и бонусов перед проведением оплаты в Kaspi.
 *
 * @param transactionId      Уникальный идентификатор транзакции / попытки оплаты.
 * @param userId             Идентификатор клиента Kaspi.
 * @param merchantId         Идентификатор мерчанта Kaspi Pay.
 * @param amount             Сумма платежа в KZT (строго положительная).
 * @param paymentMethod      Способ оплаты (QR Gold, Red+, Kredit, Alaqan и т.д.).
 * @param category           Категория мерчанта (электроника, продукты, рестораны).
 * @param city               Город проведения операции (для таргетированных промо).
 * @param installmentMonths  Срок рассрочки (0 для дебетовых платежей, 3 для Red+, 12/24 для Жұма).
 * @param timestamp          Временная метка вызова на кассе/терминале.
 */
public record EvaluatePricingRequest(
        @NotNull UUID transactionId,
        @NotNull Long userId,
        @NotNull Long merchantId,
        @NotNull @Positive BigDecimal amount,
        @NotNull PaymentMethod paymentMethod,
        @NotNull MerchantCategory category,
        String city,
        Integer installmentMonths,
        Instant timestamp
) {
    public EvaluatePricingRequest {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
        if (installmentMonths == null) {
            installmentMonths = 0;
        }
    }

    /**
     * Проверяет, оформляется ли покупка в рассрочку.
     */
    public boolean hasInstallment() {
        return (installmentMonths != null && installmentMonths > 0) || paymentMethod.isInstallment();
    }

    /**
     * Проверяет соответствие условиям крупной рассрочки Kaspi Жұма 0-0-24 (от 100 000 KZT).
     */
    public boolean isKaspiJumaEligible() {
        return amount != null && amount.compareTo(new BigDecimal("100000")) >= 0;
    }
}
