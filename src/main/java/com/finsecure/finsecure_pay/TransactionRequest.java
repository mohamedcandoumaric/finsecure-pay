
package com.finsecure.finsecure_pay;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class TransactionRequest {

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @Positive(message = "Amount must be greater than zero")
    private double amount;

    @NotBlank(message = "Currency is required")
    private String currency;

    public TransactionRequest() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
