package kz.samen.currency_exchange.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "currency_rates")
@Getter
@Setter
@NoArgsConstructor
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_currency")
    private String baseCurrency;  // e.g., "KZT"

    @Column(name = "target_currency")
    private String targetCurrency;  // e.g., "USD"

    @Column(name = "rate")
    private Double rate;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    public CurrencyRate(String baseCurrency, String targetCurrency, Double rate, LocalDate lastUpdated) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.lastUpdated = lastUpdated;
    }
}
