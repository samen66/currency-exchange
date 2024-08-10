package kz.samen.currency_exchange.domain.converter;

import kz.samen.currency_exchange.domain.dto.LimitDto;
import kz.samen.currency_exchange.domain.entity.Limit;

import java.util.List;
import java.util.stream.Collectors;

public class LimitMapper {
    private LimitMapper() {}
    public static LimitDto toDto(Limit limit) {
        if (limit == null) {
            return null;
        }
        return LimitDto.builder()
                .limitSum(limit.getLimitSum())
                .limitRemains(limit.getLimitRemains())
                .limitDatetime(limit.getLimitDatetime())
                .limitCurrencyShortname(limit.getLimitCurrencyShortname())
                .build();
    }

    public static Limit toEntity(LimitDto limitDto) {
        if (limitDto == null) {
            return null;
        }
        return new Limit(limitDto.getLimitSum(), limitDto.getLimitCurrencyShortname(), Limit.Status.ACTIVE);
    }

    public static List<LimitDto> toDtoList(List<Limit> limits) {
        return limits.stream()
                .map(LimitMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Limit> toEntityList(List<LimitDto> limitDtos) {
        return limitDtos.stream()
                .map(LimitMapper::toEntity)
                .collect(Collectors.toList());
    }
}
