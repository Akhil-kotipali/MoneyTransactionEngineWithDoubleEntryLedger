package com.akhil.paymoneytoday.dto.wallet;

import java.math.BigDecimal;

public class TransferMoneyDTO {

    private String username;
    private String password;
    private BigDecimal amount;
    private String toReceiverUsername;
    private String idempotencyKey;

    public TransferMoneyDTO() {
    }

    public TransferMoneyDTO(String username, String password, BigDecimal amount, String toReceiverUsername) {
        this.username = username;
        this.password = password;
        this.amount = amount;
        this.toReceiverUsername = toReceiverUsername;
    }

    public TransferMoneyDTO(String username, String password, BigDecimal amount, String toReceiverUsername, String idempotencyKey) {
        this.username = username;
        this.password = password;
        this.amount = amount;
        this.toReceiverUsername = toReceiverUsername;
        this.idempotencyKey = idempotencyKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getToReceiverUsername() {
        return toReceiverUsername;
    }

    public void setToReceiverUsername(String toReceiverUsername) {
        this.toReceiverUsername = toReceiverUsername;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
