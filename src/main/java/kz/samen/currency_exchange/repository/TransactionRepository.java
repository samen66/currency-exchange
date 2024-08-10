package kz.samen.currency_exchange.repository;

import kz.samen.currency_exchange.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByLimitExceeded(boolean b);
}
