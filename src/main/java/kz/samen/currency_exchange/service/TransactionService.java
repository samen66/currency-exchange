package kz.samen.currency_exchange.service;

import jakarta.transaction.Transactional;
import kz.samen.currency_exchange.domain.converter.TransactionMapper;
import kz.samen.currency_exchange.domain.dto.TransactionDto;
import kz.samen.currency_exchange.domain.entity.Transaction;
import kz.samen.currency_exchange.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final LimitService limitService;

    @Transactional
    public void recordTransaction(TransactionDto transactionDto) {
        Transaction transaction = TransactionMapper.toEntity(transactionDto);
        boolean isExceeded = false;
        if (transaction.getExpenseCategory().equals("product") || transaction.getExpenseCategory().equals("service")) {
            isExceeded = limitService.isLimitExceeded(transaction);
        }
        transaction.setLimitExceeded(isExceeded);
        transactionRepository.save(transaction);
    }

    public List<TransactionDto> findExceededTransactions() {
        return TransactionMapper.toDtoList(
                transactionRepository.findByLimitExceeded(true)
        );
    }

    public List<TransactionDto> getAllTransactions() {
        return TransactionMapper.toDtoList(transactionRepository.findAll());
    }
}
