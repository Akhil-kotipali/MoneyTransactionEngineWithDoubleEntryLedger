package com.akhil.paymoneytoday.dto.wallet;

import java.math.BigDecimal;

public class BalanceResponseDTO {

    private Long walletId;
    private String username;
    private BigDecimal balance;
    private String currency;

    public BalanceResponseDTO() {
    }

    public BalanceResponseDTO(Long walletId, String username, BigDecimal balance, String currency) {
        this.walletId = walletId;
        this.username = username;
        this.balance = balance;
        this.currency = currency;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
