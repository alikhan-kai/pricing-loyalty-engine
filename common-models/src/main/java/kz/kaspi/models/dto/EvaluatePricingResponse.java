package kz.kaspi.models.dto;

import kz.kaspi.models.enums.SplitStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Результат расчета тарифов и бонусов для моментального ответа кассе/терминалу (SLA < 15ms).
 *
 * @param transactionId              Идентификатор транзакции.
 * @param originalAmount             Исходная сумма покупки (KZT).
 * @param merchantCommissionRate     Примененная ставка эквайринг-комиссии мерчанта (например, 0.0095 = 0.95%).
 * @param merchantCommissionAmount   Удержанная комиссия мерчанта (KZT).
 * @param merchantPayoutAmount       Сумма выплаты мерчанту (Net Settlement = originalAmount - commission).
 * @param clientBonusRate            Примененная ставка бонуса для клиента (например, 0.05 = 5%).
 * @param awardedBonuses             Количество начисленных бонусов клиенту с учетом лимитов (1 бонус = 1 KZT).
 * @param remainingMonthlyLimit      Остаток месячного лимита бонусов пользователя после данной операции.
 * @param splitStatus                Статус распределения (FULL_AWARDED, SPLIT_TRANSACTION, LIMIT_REACHED_ZERO_BONUS).
 * @param splitDetail                Детализация сплита, если лимит был достигнут во время операции.
 * @param appliedCampaignCode        Код примененной маркетинговой акции (если активна).
 * @param evaluatedAt                Метка времени завершения расчета.
 */
public record EvaluatePricingResponse(
        UUID transactionId,
        BigDecimal originalAmount,
        BigDecimal merchantCommissionRate,
        BigDecimal merchantCommissionAmount,
        BigDecimal merchantPayoutAmount,
        BigDecimal clientBonusRate,
        BigDecimal awardedBonuses,
        BigDecimal remainingMonthlyLimit,
        SplitStatus splitStatus,
        SplitDetail splitDetail,
        String appliedCampaignCode,
        Instant evaluatedAt
) {
    /**
     * Проверяет, была ли транзакция разбита из-за исчерпания месячного лимита бонусов.
     */
    public boolean hasSplit() {
        return splitStatus == SplitStatus.SPLIT_TRANSACTION;
    }

    /**
     * Проверяет, был ли исчерпан месячный лимит бонусов до совершения операции.
     */
    public boolean isLimitExceeded() {
        return splitStatus == SplitStatus.LIMIT_REACHED_ZERO_BONUS;
    }

    /**
     * Проверяет, начислены ли бонусы по транзакции.
     */
    public boolean hasBonusesAwarded() {
        return awardedBonuses != null && awardedBonuses.compareTo(BigDecimal.ZERO) > 0;
    }
}
