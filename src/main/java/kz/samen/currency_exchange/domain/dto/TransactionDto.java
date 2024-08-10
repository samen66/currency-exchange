package kz.samen.currency_exchange.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @JsonProperty("account_from")
    private String accountFrom;

    @JsonProperty("account_to")
    private String accountTo;

    @JsonProperty("currency_shortname")
    private String currencyShortname;

    @JsonProperty("sum")
    private BigDecimal sum;

    @JsonProperty("expense_category")
    private String expenseCategory;

    @JsonProperty("datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssX")
    private ZonedDateTime datetime;
}
