package kz.samen.currency_exchange.service;

import kz.samen.currency_exchange.client.CurrencyApiClient;
import kz.samen.currency_exchange.domain.entity.CurrencyRate;
import kz.samen.currency_exchange.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyApiClient currencyApiClient;

    public void updateDailyRates() {
        updateRateForCurrencyPair("USD", "KZT", "RUB");
    }

    private void updateRateForCurrencyPair(String baseCurrency, String... targetCurrencies) {
        LocalDate today = LocalDate.now();

        Map<String, Double> fetchedRate = currencyApiClient.getCurrencyRates(baseCurrency, targetCurrencies);
        if (fetchedRate != null && !fetchedRate.isEmpty()) {
            for (Map.Entry<String, Double> entry : fetchedRate.entrySet()) {
                String targetCurrency = entry.getKey();
                Double rate = entry.getValue();
                CurrencyRate currencyRate = currencyRateRepository.findByBaseCurrencyAndTargetCurrency(
                        baseCurrency, targetCurrency
                ).orElse(new CurrencyRate());

                currencyRate.setBaseCurrency(baseCurrency);
                currencyRate.setTargetCurrency(targetCurrency);
                currencyRate.setRate(rate);
                currencyRate.setLastUpdated(today);
                currencyRateRepository.save(currencyRate);
            }
        }
    }
}
