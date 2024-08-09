package kz.samen.currency_exchange.service;

import kz.samen.currency_exchange.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitService {
    private final LimitRepository limitRepository;
}
