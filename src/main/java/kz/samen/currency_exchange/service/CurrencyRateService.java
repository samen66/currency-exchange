package kz.samen.currency_exchange.service;

import kz.samen.currency_exchange.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {
    private final CurrencyRateRepository CurrencyRateRepository;
}
