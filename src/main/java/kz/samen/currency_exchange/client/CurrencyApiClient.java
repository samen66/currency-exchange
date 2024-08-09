package kz.samen.currency_exchange.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyApiClient {
    @Value("${currency-api.api-key}")
    private String apiKey;
    @Value("${currency-api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    /**
     * Fetches currency exchange rates for specified currencies.
     * @param currencyCode Comma-separated currency codes (e.g., "KZT,RUB").
     * @return A map of currency codes to their respective rates in USD.
     */
    public Map<String, Double> getCurrencyRates(String baseCurrency, String ... currencyCode) {
        String currencies = String.join(",", currencyCode);
        String url = String.format("%s?apikey=%s&currencies=%s&base_currency=%s", baseUrl, apiKey, currencies, baseCurrency);
        try {
            CurrencyApiResponse response = restTemplate.getForObject(url, CurrencyApiResponse.class);
            return extractRates(response);
        } catch (HttpClientErrorException e) {
            log.error("Failed to fetch currency rates: {}", e.getMessage());
            return Map.of(); // Return an empty map in case of an error
        }
    }

    /**
     * Extracts rates from the API response.
     * @param response The response from the currency API.
     * @return A map of currency codes to rates.
     */
    private Map<String, Double> extractRates(CurrencyApiResponse response) {
        return response.getData().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getValue()));
    }

    /**
     * Internal class to model the JSON response structure from the CurrencyAPI.
     */
    @Getter
    private static class CurrencyApiResponse {
        private Map<String, CurrencyData> data;

    }

    @Getter
    private static class CurrencyData {
        private Double value;

    }
}
