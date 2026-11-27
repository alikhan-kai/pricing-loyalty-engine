package kz.kaspi.pricing.service;

import kz.kaspi.models.constant.KaspiPricingConstants;
import kz.kaspi.models.enums.SplitStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

/**
 * Реактивный сервис атомарного резервирования бонусов в Redis с контролем лимита 30 000.
 */
@Service
@RequiredArgsConstructor
public class BonusReservationService {

    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private final RedisScript<List> atomicBonusReserveScript;

    public record ReservationResult(
            BigDecimal awardedBonuses,
            BigDecimal remainingLimit,
            SplitStatus splitStatus
    ) {}

    /**
     * Атомарно проверяет и резервирует бонусы в Redis за один сетевой раунд-трип.
     */
    public Mono<ReservationResult> reserveBonuses(Long userId, BigDecimal calculatedBonuses) {
        if (calculatedBonuses == null || calculatedBonuses.compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.just(new ReservationResult(BigDecimal.ZERO, KaspiPricingConstants.MONTHLY_BONUS_CAP, SplitStatus.NO_PROMO));
        }

        String currentMonth = LocalDate.now().format(MONTH_FORMATTER);
        String redisKey = String.format(KaspiPricingConstants.REDIS_BONUS_MONTH_KEY_PATTERN, userId, currentMonth);

        List<String> keys = Collections.singletonList(redisKey);
        String requestedStr = calculatedBonuses.toPlainString();
        String capStr = KaspiPricingConstants.MONTHLY_BONUS_CAP.toPlainString();
        String ttlStr = "3024000";

        return reactiveRedisTemplate.execute(atomicBonusReserveScript, keys, List.of(requestedStr, capStr, ttlStr))
                .next()
                .map(rawList -> {
                    List<?> list = (List<?>) rawList;
                    BigDecimal awarded = new BigDecimal(list.get(0).toString());
                    BigDecimal remaining = new BigDecimal(list.get(1).toString());
                    SplitStatus status = SplitStatus.valueOf(list.get(2).toString());
                    return new ReservationResult(awarded, remaining, status);
                });
    }
}
