package kz.kaspi.models.event;

import kz.kaspi.models.enums.MerchantCategory;
import kz.kaspi.models.enums.PaymentMethod;
import kz.kaspi.models.enums.SplitStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Неизменяемое событие для публикации в Kafka топик 'pricing-ledger-events'.
 * Используется сервисом 'bonus-ledger-service' для создания бухгалтерских проводок
 * двойной записи в PostgreSQL и потоковой индексации в Elasticsearch.
 */
public record PricingLedgerEvent(
        UUID transactionId,
        Long userId,
        Long merchantId,
        BigDecimal amount,
        BigDecimal merchantCommissionAmount,
        BigDecimal merchantPayoutAmount,
        BigDecimal awardedBonuses,
        PaymentMethod paymentMethod,
        MerchantCategory category,
        String city,
        SplitStatus splitStatus,
        String appliedCampaignCode,
        Instant occurredAt,
        String traceId
) {
}
