package com.akhil.paymoneytoday.models;

import com.akhil.paymoneytoday.enums.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_wallet_id")
    private Long senderWalletId;

    @Column(name = "receiver_wallet_id", nullable = false)
    private Long receiverWalletId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "sender_balance_before", precision = 19, scale = 4)
    private BigDecimal senderBalanceBefore;

    @Column(name = "sender_balance_after", precision = 19, scale = 4)
    private BigDecimal senderBalanceAfter;

    @Column(name = "receiver_balance_before", precision = 19, scale = 4)
    private BigDecimal receiverBalanceBefore;

    @Column(name = "receiver_balance_after", precision = 19, scale = 4)
    private BigDecimal receiverBalanceAfter;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;

    public Transaction() {
    }

    public Transaction(Long senderWalletId, Long receiverWalletId, BigDecimal amount,
                       BigDecimal senderBalanceBefore, BigDecimal senderBalanceAfter,
                       BigDecimal receiverBalanceBefore, BigDecimal receiverBalanceAfter,
                       TransactionStatus status) {
        this.senderWalletId = senderWalletId;
        this.receiverWalletId = receiverWalletId;
        this.amount = amount;
        this.senderBalanceBefore = senderBalanceBefore;
        this.senderBalanceAfter = senderBalanceAfter;
        this.receiverBalanceBefore = receiverBalanceBefore;
        this.receiverBalanceAfter = receiverBalanceAfter;
        this.status = status;
    }

    public Transaction(Long id, Long senderWalletId, Long receiverWalletId, BigDecimal amount,
                       BigDecimal senderBalanceBefore, BigDecimal senderBalanceAfter,
                       BigDecimal receiverBalanceBefore, BigDecimal receiverBalanceAfter,
                       LocalDateTime createdAt, TransactionStatus status) {
        this.id = id;
        this.senderWalletId = senderWalletId;
        this.receiverWalletId = receiverWalletId;
        this.amount = amount;
        this.senderBalanceBefore = senderBalanceBefore;
        this.senderBalanceAfter = senderBalanceAfter;
        this.receiverBalanceBefore = receiverBalanceBefore;
        this.receiverBalanceAfter = receiverBalanceAfter;
        this.createdAt = createdAt;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = TransactionStatus.PENDING;
        }
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

    public Long getReceiverWalletId() {
        return receiverWalletId;
    }

    public void setReceiverWalletId(Long receiverWalletId) {
        this.receiverWalletId = receiverWalletId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
