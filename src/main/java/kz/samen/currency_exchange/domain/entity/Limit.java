package kz.samen.currency_exchange.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "limits")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "limit_sum")
    private BigDecimal limitSum;

    @Column(name = "limit_datetime")
    private LocalDateTime limitDatetime;

    @Column(name = "limit_currency_shortname")
    private String limitCurrencyShortname;
}
