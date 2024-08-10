package kz.samen.currency_exchange.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "limits")
@Getter
@NoArgsConstructor
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "limit_sum")
    private BigDecimal limitSum;

    @Column(name = "limit_remains")
    @Setter
    private BigDecimal limitRemains;

    @Column(name = "limit_datetime")
    private ZonedDateTime limitDatetime;

    @Column(name = "limit_currency_shortname")
    private String limitCurrencyShortname;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Setter
    private Limit.Status status;

    public Limit(BigDecimal limitSum, String limitCurrencyShortname, Limit.Status status) {
        this.limitSum = Objects.requireNonNullElse(limitSum, BigDecimal.ZERO);
        this.limitRemains = limitSum;
        this.limitDatetime = ZonedDateTime.now();
        if (limitCurrencyShortname == null || limitCurrencyShortname.isEmpty()) {
            this.limitCurrencyShortname = "USD";
        } else {
            this.limitCurrencyShortname = limitCurrencyShortname;
        }
        this.status = status;
    }

    public enum Status{
        ACTIVE,
        INACTIVE
    }
}
