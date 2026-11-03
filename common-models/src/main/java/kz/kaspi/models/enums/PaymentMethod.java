package kz.kaspi.models.enums;

/**
 * Способы оплаты в экосистеме Kaspi.
 */
public enum PaymentMethod {
    /**
     * Оплата с Kaspi Gold / кошелька через Kaspi QR на терминале мерчанта.
     */
    KASPI_QR_GOLD,

    /**
     * Клубная беспроцентная рассрочка на 3 месяца через Kaspi Red+.
     */
    KASPI_RED_PLUS,

    /**
     * Покупки в рассрочку/кредит на долгий срок (включая Kaspi Жұма 0-0-24).
     */
    KASPI_KREDIT,

    /**
     * Оплата картами других банков через Единый QR.
     */
    OTHER_BANK_QR,

    /**
     * Оплата биометрией (Kaspi Alaqan / ладонь).
     */
    KASPI_ALAQAN
}
