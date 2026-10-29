
package com.finsecure.finsecure_pay;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
public class TransactionController {

    private static final Set<String> SUPPORTED_CURRENCIES =
            Set.of("SGD", "USD", "EUR");

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateTransaction(
            @Valid @RequestBody TransactionRequest request) {

        String currency = request.getCurrency().toUpperCase();

        if (!SUPPORTED_CURRENCIES.contains(currency)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "REJECTED",
                    "message", "Unsupported currency"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "status", "APPROVED",
                "transactionId", request.getTransactionId(),
                "amount", request.getAmount(),
                "currency", currency,
                "message", "Transaction validation successful"
        ));
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid transaction");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "status", "REJECTED",
                "message", message
        ));
    }
}
