package kz.kaspi.models.constant;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Общесистемные константы финансовой математики и лимитов Kaspi Loyalty & Pricing Engine.
 */
public final class KaspiPricingConstants {

    private KaspiPricingConstants() {
        // Утилитный класс констант не инстанциируется
    }

    /**
     * Стандартный месячный лимит бонусов на одного пользователя при покупках через QR / Red+ / Gold.
     * 30 000 бонусов = 30 000 KZT.
     */
    public static final BigDecimal MONTHLY_BONUS_CAP = new BigDecimal("30000");

    /**
     * Повышенный месячный лимит бонусов в Магазине на Kaspi.kz и Kaspi Travel (туры/жд).
     */
    public static final BigDecimal MARKETPLACE_MONTHLY_BONUS_CAP = new BigDecimal("500000");

    /**
     * Банковское округление (Banker's rounding - HALF_EVEN) для предотвращения систематического смещения.
     */
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;

    /**
     * Точность (масштаб) для хранения денежных сумм в KZT.
     */
    public static final int MONEY_SCALE = 2;

    /**
     * Точность для хранения бонусов (целые единицы, 1 бонус = 1 KZT).
     */
    public static final int BONUS_SCALE = 0;

    /**
     * Базовая валюта операций.
     */
    public static final String DEFAULT_CURRENCY = "KZT";

    /**
     * Базовая ставка эквайринга мерчанта при оплате с Kaspi Gold / QR (0.95%).
     */
    public static final BigDecimal DEFAULT_BASE_QR_COMMISSION = new BigDecimal("0.0095");

    /**
     * Ставка эквайринга при оплате картами других банков через Единый QR (1.30%).
     */
    public static final BigDecimal DEFAULT_OTHER_BANK_QR_COMMISSION = new BigDecimal("0.0130");

    /**
     * Название топика Kafka для финансового леджера и аналитики.
     */
    public static final String TOPIC_PRICING_LEDGER_EVENTS = "pricing-ledger-events";

    /**
     * Целевой SLA времени вычисления тарифа и бонусов (до 15 мс).
     */
    public static final long TARGET_SLA_MILLIS = 15L;

    /**
     * Системный идентификатор общенациональной акции Kaspi Жұма.
     */
    public static final String CAMPAIGN_KASPI_JUMA = "KASPI_JUMA";

    /**
     * Системный идентификатор клубной акции рассрочки Kaspi Red+.
     */
    public static final String CAMPAIGN_KASPI_RED_PLUS = "KASPI_RED_PLUS";

    /**
     * Системный идентификатор базовой бонусной промо-кампании.
     */
    public static final String CAMPAIGN_BASE_PROMO = "KASPI_BASE_PROMO";
}
