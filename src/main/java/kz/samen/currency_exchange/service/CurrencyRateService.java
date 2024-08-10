package kz.samen.currency_exchange.service;

import kz.samen.currency_exchange.client.CurrencyApiClient;
import kz.samen.currency_exchange.domain.entity.CurrencyRate;
import kz.samen.currency_exchange.exception.CurrencyRateNotFoundException;
import kz.samen.currency_exchange.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyApiClient currencyApiClient;

    public void updateDailyRates() {
        updateRateForCurrencyPair("USD", "KZT", "RUB");
    }

    private CurrencyRate updateRateForCurrencyPair(String baseCurrency, String... targetCurrencies) {
        CurrencyRate currencyRate = null;

        Map<String, Double> fetchedRate = currencyApiClient.getCurrencyRates(baseCurrency, targetCurrencies);
        if (fetchedRate != null && !fetchedRate.isEmpty()) {
            for (Map.Entry<String, Double> entry : fetchedRate.entrySet()) {
                String targetCurrency = entry.getKey();
                Double rate = entry.getValue();
                currencyRate = currencyRateRepository.findByBaseCurrencyAndTargetCurrency(
                        baseCurrency, targetCurrency
                ).orElse(new CurrencyRate());

                currencyRate.setBaseCurrency(baseCurrency);
                currencyRate.setTargetCurrency(targetCurrency);
                currencyRate.setRate(rate);
                currencyRate.setLastUpdated(ZonedDateTime.now());
            }
        }
        return currencyRate == null ? null : currencyRateRepository.save(currencyRate);
    }

    public BigDecimal convertCurrency(String from, String to, BigDecimal sum) throws CurrencyRateNotFoundException {
        CurrencyRate currencyRate = null;
        //get today currency rate
        Optional<CurrencyRate> optionalCurrencyRate = currencyRateRepository
                .findByBaseCurrencyAndTargetCurrencyAndLastUpdatedIsGreaterThanEqual(
                        from, to, ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS)
                );

        //if there is no today currency rate update currency rate
        if (optionalCurrencyRate.isEmpty()) {
            currencyRate = updateRateForCurrencyPair(from, to);
        }
        //if today currency rate not updated
        if (currencyRate == null) {
            //get yesterday currency rate
            Optional<CurrencyRate> yesterdayOptionalCurrencyRate = currencyRateRepository
                    .findByBaseCurrencyAndTargetCurrencyAndLastUpdatedIsGreaterThanEqual(
                            from, to, ZonedDateTime.now().minusDays(1)
                    );
            //if yesterday currency rate null throw exception
            if (yesterdayOptionalCurrencyRate.isEmpty()) {
                throw new CurrencyRateNotFoundException("currency rate from " + from + " to " + to + " not found");
            }
            currencyRate = yesterdayOptionalCurrencyRate.get();
        }
        return sum.divide(BigDecimal.valueOf(currencyRate.getRate()), 2, RoundingMode.HALF_UP);
    }
}
