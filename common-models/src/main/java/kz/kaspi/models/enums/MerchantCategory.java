package kz.kaspi.models.enums;

import java.math.BigDecimal;

/**
 * Категории деятельности мерчантов Kaspi Pay с базовыми тарифами эквайринга.
 */
public enum MerchantCategory {
    /**
     * Продуктовые супермаркеты, магазины у дома (низкомаржинальный сектор).
     */
    GROCERY(new BigDecimal("0.0095"), new BigDecimal("0.052")),

    /**
     * Бытовая техника, электроника, смартфоны.
     */
    ELECTRONICS(new BigDecimal("0.0095"), new BigDecimal("0.085")),

    /**
     * Одежда, обувь, аксессуары.
     */
    CLOTHING(new BigDecimal("0.0095"), new BigDecimal("0.095")),

    /**
     * Аптеки, медикаменты, оптика.
     */
    PHARMACY(new BigDecimal("0.0095"), new BigDecimal("0.052")),

    /**
     * Рестораны, кофейни, доставка еды.
     */
    RESTAURANT(new BigDecimal("0.0095"), new BigDecimal("0.075")),

    /**
     * Услуги, салоны красоты, фитнес, медицина, аренда.
     */
    SERVICES(new BigDecimal("0.0095"), new BigDecimal("0.130")),

    /**
     * Авиа и ЖД билеты, туризм.
     */
    TRAVEL(new BigDecimal("0.0095"), new BigDecimal("0.046")),

    /**
     * Автозаправочные станции (АЗС).
     */
    PETROL_STATION(new BigDecimal("0.0095"), new BigDecimal("0.046")),

    /**
     * Прочие виды деятельности.
     */
    OTHER(new BigDecimal("0.0095"), new BigDecimal("0.090"));

    private final BigDecimal baseQrCommissionRate;
    private final BigDecimal installmentCommissionRate;

    MerchantCategory(BigDecimal baseQrCommissionRate, BigDecimal installmentCommissionRate) {
        this.baseQrCommissionRate = baseQrCommissionRate;
        this.installmentCommissionRate = installmentCommissionRate;
    }

    public BigDecimal getBaseQrCommissionRate() {
        return baseQrCommissionRate;
    }

    public BigDecimal getInstallmentCommissionRate() {
        return installmentCommissionRate;
    }

    /**
     * Возвращает базовую ставку комиссии мерчанта в зависимости от способа оплаты.
     *
     * @param paymentMethod способ проведения платежа клиентом
     * @return коэффициент комиссии (например, 0.0095 для 0.95%)
     */
    public BigDecimal resolveCommissionRate(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            return baseQrCommissionRate;
        }
        return switch (paymentMethod) {
            case KASPI_QR_GOLD, KASPI_ALAQAN -> baseQrCommissionRate;
            case KASPI_RED_PLUS, KASPI_KREDIT -> installmentCommissionRate;
            case OTHER_BANK_QR -> new BigDecimal("0.0130");
        };
    }
}
