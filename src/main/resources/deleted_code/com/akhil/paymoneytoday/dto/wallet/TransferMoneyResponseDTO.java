package com.akhil.paymoneytoday.dto.wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferMoneyResponseDTO {

    private String senderName;
    private String receiverName;
    private BigDecimal amount;
    private BigDecimal balanceRemaining;
    private LocalDateTime time;

    public TransferMoneyResponseDTO() {
    }

    public TransferMoneyResponseDTO(String senderName, String receiverName, BigDecimal amount, BigDecimal balanceRemaining, LocalDateTime time) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.amount = amount;
        this.balanceRemaining = balanceRemaining;
        this.time = time;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceRemaining() {
        return balanceRemaining;
    }

    public void setBalanceRemaining(BigDecimal balanceRemaining) {
        this.balanceRemaining = balanceRemaining;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
