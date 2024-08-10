package kz.samen.currency_exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class LimitDto {
    @JsonProperty("limit_sum")
    private BigDecimal limitSum;

    @JsonProperty("limit_remains")
    private BigDecimal limitRemains;

    @JsonProperty("limit_datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssX")
    private ZonedDateTime limitDatetime;

    @JsonProperty("limit_currency_shortname")
    private String limitCurrencyShortname;
}
