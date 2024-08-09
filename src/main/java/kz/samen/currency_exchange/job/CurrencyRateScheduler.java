package kz.samen.currency_exchange.job;

import kz.samen.currency_exchange.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyRateScheduler {
    private final CurrencyRateService currencyRateService;

    @Scheduled(cron = "0 42 11 * * ?") // Runs at 00:00 every day
    public void updateCurrencyRates() {
        LocalDateTime startTime = LocalDateTime.now();
        log.info("started Updating currency rates");

        currencyRateService.updateDailyRates();

        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        log.info("Ended updating currency rates. Duration: {} nano seconds", duration.getNano());
    }
}
