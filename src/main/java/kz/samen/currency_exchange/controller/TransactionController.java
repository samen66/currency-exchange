package kz.samen.currency_exchange.controller;

import kz.samen.currency_exchange.domain.dto.TransactionDto;
import kz.samen.currency_exchange.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity recordTransaction(@RequestBody TransactionDto transaction) {
        transactionService.recordTransaction(transaction);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exceeded")
    public ResponseEntity<List<TransactionDto>> getExceededTransactions() {
        return ResponseEntity.ok(transactionService.findExceededTransactions());
    }
    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}
