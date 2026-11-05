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
}
