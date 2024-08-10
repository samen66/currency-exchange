package kz.samen.currency_exchange.controller;

import kz.samen.currency_exchange.domain.dto.LimitDto;
import kz.samen.currency_exchange.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/limits")
public class LimitController {
    private final LimitService limitService;

    @PostMapping
    public ResponseEntity<?> setNewLimit(@RequestBody LimitDto limit) {
        if (limit.getLimitSum().compareTo(BigDecimal.ZERO) < 0){
            return ResponseEntity.badRequest().body("limit sum must be greater than zero");
        }
        limitService.setNewLimit(limit);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<LimitDto>> getAllLimits() {
        return ResponseEntity.ok(limitService.findAllLimits());
    }
}
