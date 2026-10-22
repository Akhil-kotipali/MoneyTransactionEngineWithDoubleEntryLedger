package com.akhil.paymoneytoday.dto.wallet;

import com.akhil.paymoneytoday.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {

    private Long id;
    private Long senderWalletId;
    private String senderUsername;
    private Long receiverWalletId;
    private String receiverUsername;
    private BigDecimal amount;
    private BigDecimal senderBalanceBefore;
    private BigDecimal senderBalanceAfter;
    private BigDecimal receiverBalanceBefore;
    private BigDecimal receiverBalanceAfter;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private String idempotencyKey;

    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(Long id, Long senderWalletId, String senderUsername,
                                  Long receiverWalletId, String receiverUsername,
                                  BigDecimal amount, BigDecimal senderBalanceBefore,
                                  BigDecimal senderBalanceAfter, BigDecimal receiverBalanceBefore,
                                  BigDecimal receiverBalanceAfter, TransactionStatus status,
                                  LocalDateTime createdAt) {
        this.id = id;
        this.senderWalletId = senderWalletId;
        this.senderUsername = senderUsername;
        this.receiverWalletId = receiverWalletId;
        this.receiverUsername = receiverUsername;
        this.amount = amount;
        this.senderBalanceBefore = senderBalanceBefore;
        this.senderBalanceAfter = senderBalanceAfter;
        this.receiverBalanceBefore = receiverBalanceBefore;
        this.receiverBalanceAfter = receiverBalanceAfter;
        this.status = status;
        this.createdAt = createdAt;
    }

    public TransactionResponseDTO(Long id, Long senderWalletId, String senderUsername,
                                  Long receiverWalletId, String receiverUsername,
                                  BigDecimal amount, BigDecimal senderBalanceBefore,
                                  BigDecimal senderBalanceAfter, BigDecimal receiverBalanceBefore,
                                  BigDecimal receiverBalanceAfter, TransactionStatus status,
                                  LocalDateTime createdAt, String idempotencyKey) {
        this.id = id;
        this.senderWalletId = senderWalletId;
        this.senderUsername = senderUsername;
        this.receiverWalletId = receiverWalletId;
        this.receiverUsername = receiverUsername;
        this.amount = amount;
        this.senderBalanceBefore = senderBalanceBefore;
        this.senderBalanceAfter = senderBalanceAfter;
        this.receiverBalanceBefore = receiverBalanceBefore;
        this.receiverBalanceAfter = receiverBalanceAfter;
        this.status = status;
        this.createdAt = createdAt;
        this.idempotencyKey = idempotencyKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderWalletId() {
        return senderWalletId;
    }

    public void setSenderWalletId(Long senderWalletId) {
        this.senderWalletId = senderWalletId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public Long getReceiverWalletId() {
        return receiverWalletId;
    }

    public void setReceiverWalletId(Long receiverWalletId) {
        this.receiverWalletId = receiverWalletId;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSenderBalanceBefore() {
        return senderBalanceBefore;
    }

    public void setSenderBalanceBefore(BigDecimal senderBalanceBefore) {
        this.senderBalanceBefore = senderBalanceBefore;
    }

    public BigDecimal getSenderBalanceAfter() {
        return senderBalanceAfter;
    }

    public void setSenderBalanceAfter(BigDecimal senderBalanceAfter) {
        this.senderBalanceAfter = senderBalanceAfter;
    }

    public BigDecimal getReceiverBalanceBefore() {
        return receiverBalanceBefore;
    }

    public void setReceiverBalanceBefore(BigDecimal receiverBalanceBefore) {
        this.receiverBalanceBefore = receiverBalanceBefore;
    }

    public BigDecimal getReceiverBalanceAfter() {
        return receiverBalanceAfter;
    }

    public void setReceiverBalanceAfter(BigDecimal receiverBalanceAfter) {
        this.receiverBalanceAfter = receiverBalanceAfter;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
