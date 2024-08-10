package kz.samen.currency_exchange.service;

import kz.samen.currency_exchange.domain.converter.LimitMapper;
import kz.samen.currency_exchange.domain.dto.LimitDto;
import kz.samen.currency_exchange.domain.entity.Limit;
import kz.samen.currency_exchange.domain.entity.Transaction;
import kz.samen.currency_exchange.exception.CurrencyRateNotFoundException;
import kz.samen.currency_exchange.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LimitService {

    @Value("${currency.limit.sum}")
    public BigDecimal defaultLimitSum;

    @Value("${currency.limit.currency.code}")
    public String defaultLimitCurrencyCode;


    private final LimitRepository limitRepository;
    private final CurrencyRateService currencyRateService;

    public boolean isLimitExceeded(Transaction transaction) {
        //get limit for this month
        Limit limit = getLatestLimitForCurrentMonth();

        //get remainder limit
        BigDecimal limitRemains = limit.getLimitRemains();
        BigDecimal transactionUSDSum;
        boolean isLimitExceeded = false;

        if (transaction.getCurrencyShortname().equals(limit.getLimitCurrencyShortname())) {
            transactionUSDSum = transaction.getSum();
        } else {
            try {
                transactionUSDSum = currencyRateService.convertCurrency(
                        limit.getLimitCurrencyShortname(),
                        transaction.getCurrencyShortname(),
                        transaction.getSum()
                );
            } catch (CurrencyRateNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        isLimitExceeded = limitRemains.compareTo(transactionUSDSum) < 0;
        BigDecimal newLimitReminds = limitRemains.subtract(transactionUSDSum);
        limit.setLimitRemains(newLimitReminds.compareTo(BigDecimal.ZERO) > 0 ? newLimitReminds : BigDecimal.ZERO);
        limitRepository.save(limit);


        return isLimitExceeded;
    }

    public Limit getLatestLimitForCurrentMonth() {
        ZonedDateTime today = ZonedDateTime.now();

        // Adjust to the first day of the month and truncate to midnight
        ZonedDateTime firstDayOfMonthAtMidnight = today.with(TemporalAdjusters.firstDayOfMonth())
                .truncatedTo(ChronoUnit.DAYS);

        Optional<Limit> optionalLimit = limitRepository.findLimitByCreatedAtAndStatus(
                firstDayOfMonthAtMidnight, today, Limit.Status.ACTIVE);
        if (optionalLimit.isPresent()) {
            return optionalLimit.get();
        }
        Limit defaultLimit = new Limit(defaultLimitSum, defaultLimitCurrencyCode, Limit.Status.ACTIVE);
        return limitRepository.save(defaultLimit);
    }

    public void setNewLimit(LimitDto limitDto) {
        Limit limit = LimitMapper.toEntity(limitDto);

        ZonedDateTime today = ZonedDateTime.now();
        ZonedDateTime firstDayOfMonthAtMidnight = today.with(TemporalAdjusters.firstDayOfMonth())
                .truncatedTo(ChronoUnit.DAYS);

        Optional<Limit> optionalLimit = limitRepository.findLimitByCreatedAtAndStatus(firstDayOfMonthAtMidnight, today,
                Limit.Status.ACTIVE);
        if (optionalLimit.isPresent()) {
            optionalLimit.get().setStatus(Limit.Status.INACTIVE);
            limitRepository.save(optionalLimit.get());
        }
        limitRepository.save(limit);
    }

    public List<LimitDto> findAllLimits() {
        return LimitMapper.toDtoList(limitRepository.findAll());
    }
}
