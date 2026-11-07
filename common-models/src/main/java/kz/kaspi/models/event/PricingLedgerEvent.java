package kz.kaspi.models.event;

import kz.kaspi.models.dto.EvaluatePricingRequest;
import kz.kaspi.models.dto.EvaluatePricingResponse;
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
    /**
     * Фабричный метод формирования события для Kafka на основе запроса и рассчитанного ответа.
     */
    public static PricingLedgerEvent of(
            EvaluatePricingRequest request,
            EvaluatePricingResponse response,
            String traceId
    ) {
        return new PricingLedgerEvent(
                request.transactionId(),
                request.userId(),
                request.merchantId(),
                request.amount(),
                response.merchantCommissionAmount(),
                response.merchantPayoutAmount(),
                response.awardedBonuses(),
                request.paymentMethod(),
                request.category(),
                request.city(),
                response.splitStatus(),
                response.appliedCampaignCode(),
                Instant.now(),
                traceId
        );
    }

    /**
     * Проверяет, предполагает ли событие начисление бонусов клиенту.
     */
    public boolean hasBonuses() {
        return awardedBonuses != null && awardedBonuses.compareTo(BigDecimal.ZERO) > 0;
    }
}
