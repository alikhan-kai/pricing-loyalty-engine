package kz.kaspi.pricing.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import kz.kaspi.pricing.model.MerchantProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Конфигурация локального L1-кэша Caffeine для ультрабыстрого чтения профилей мерчантов.
 */
@Configuration
public class CacheConfig {

    @Bean
    public Cache<Long, MerchantProfile> merchantProfileCache() {
        return Caffeine.newBuilder()
                .maximumSize(50_000)
                .expireAfterWrite(Duration.ofMinutes(15))
                .recordStats()
                .build();
    }
}
