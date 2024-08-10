package kz.samen.currency_exchange.repository;

import kz.samen.currency_exchange.domain.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findByBaseCurrencyAndTargetCurrency(String baseCurrency, String targetCurrency);
    Optional<CurrencyRate> findByBaseCurrencyAndTargetCurrencyAndLastUpdatedIsGreaterThanEqual(
            String baseCurrency, String targetCurrency, ZonedDateTime lastUpdated
    );
}
