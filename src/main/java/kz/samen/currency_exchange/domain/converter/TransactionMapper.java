package kz.samen.currency_exchange.domain.converter;

import kz.samen.currency_exchange.domain.dto.TransactionDto;
import kz.samen.currency_exchange.domain.entity.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper {
    private TransactionMapper() {}
    public static TransactionDto toDto(Transaction transaction) {
        return TransactionDto.builder()
                .accountFrom(transaction.getAccountFrom())
                .accountTo(transaction.getAccountTo())
                .currencyShortname(transaction.getCurrencyShortname())
                .sum(transaction.getSum())
                .expenseCategory(transaction.getExpenseCategory())
                .datetime(transaction.getDatetime())
                .limitExceeded(transaction.isLimitExceeded())
                .build();
    }

    public static Transaction toEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setAccountFrom(transactionDto.getAccountFrom());
        transaction.setAccountTo(transactionDto.getAccountTo());
        transaction.setCurrencyShortname(transactionDto.getCurrencyShortname());
        transaction.setSum(transactionDto.getSum());
        transaction.setExpenseCategory(transactionDto.getExpenseCategory());
        transaction.setDatetime(transactionDto.getDatetime());
        return transaction;
    }

    public static List<TransactionDto> toDtoList(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Transaction> toEntityList(List<TransactionDto> transactionDtos) {
        return transactionDtos.stream()
                .map(TransactionMapper::toEntity)
                .collect(Collectors.toList());
    }
}
