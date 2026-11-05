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
    KASPI_ALAQAN;

    /**
     * Проверяет, является ли способ оплаты рассрочкой или кредитом (Red+, Kredit).
     */
    public boolean isInstallment() {
        return this == KASPI_RED_PLUS || this == KASPI_KREDIT;
    }

    /**
     * Проверяет, совершается ли оплата собственными дебетовыми средствами клиента (Gold, Alaqan).
     */
    public boolean isDebit() {
        return this == KASPI_QR_GOLD || this == KASPI_ALAQAN;
    }
}
