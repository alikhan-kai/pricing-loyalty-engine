package kz.kaspi.pricing.model;

import kz.kaspi.models.enums.MerchantCategory;

import java.math.BigDecimal;

/**
 * Кэшируемый профиль мерчанта для мгновенного доступа в критическом пути расчета (SLA < 15ms).
 *
 * @param merchantId         Идентификатор мерчанта.
 * @param merchantName       Наименование юридического лица / торговой точки.
 * @param category           Категория деятельности мерчанта.
 * @param customQrRate       Индивидуальная договорная ставка эквайринга (если отличается от стандартной).
 * @param active             Признак активности мерчанта и разрешения эквайринга.
 */
public record MerchantProfile(
        Long merchantId,
        String merchantName,
        MerchantCategory category,
        BigDecimal customQrRate,
        boolean active
) {
    public static MerchantProfile ofDefault(Long merchantId, String name, MerchantCategory category) {
        return new MerchantProfile(merchantId, name, category, null, true);
    }
}
