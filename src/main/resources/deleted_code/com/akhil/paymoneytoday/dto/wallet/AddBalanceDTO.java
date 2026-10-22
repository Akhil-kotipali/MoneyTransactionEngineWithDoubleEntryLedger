package com.akhil.paymoneytoday.dto.wallet;

import java.math.BigDecimal;

public class AddBalanceDTO {

    private Long walletId;
    private String username;
    private BigDecimal amount;
    private String idempotencyKey;

    public AddBalanceDTO() {
    }

    public AddBalanceDTO(Long walletId, String username, BigDecimal amount) {
        this.walletId = walletId;
        this.username = username;
        this.amount = amount;
    }

    public AddBalanceDTO(Long walletId, String username, BigDecimal amount, String idempotencyKey) {
        this.walletId = walletId;
        this.username = username;
        this.amount = amount;
        this.idempotencyKey = idempotencyKey;
    }

    public AddBalanceDTO(String username, BigDecimal amount) {
        this.username = username;
        this.amount = amount;
    }

    public AddBalanceDTO(Long walletId, BigDecimal amount) {
        this.walletId = walletId;
        this.amount = amount;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
