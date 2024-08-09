package kz.samen.currency_exchange.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "currency_rates")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_pair")
    private String currencyPair;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "date")
    private LocalDate date;
}
