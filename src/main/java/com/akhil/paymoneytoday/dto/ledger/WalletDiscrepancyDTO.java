package com.akhil.paymoneytoday.dto.ledger;

import java.math.BigDecimal;

public class WalletDiscrepancyDTO {

    private Long walletId;
    private BigDecimal materializedBalance;
    private BigDecimal replayedBalance;
    private BigDecimal discrepancy;

    public WalletDiscrepancyDTO() {
    }

    public WalletDiscrepancyDTO(Long walletId, BigDecimal materializedBalance, BigDecimal replayedBalance, BigDecimal discrepancy) {
        this.walletId = walletId;
        this.materializedBalance = materializedBalance;
        this.replayedBalance = replayedBalance;
        this.discrepancy = discrepancy;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getMaterializedBalance() {
        return materializedBalance;
    }

    public void setMaterializedBalance(BigDecimal materializedBalance) {
        this.materializedBalance = materializedBalance;
    }

    public BigDecimal getReplayedBalance() {
        return replayedBalance;
    }

    public void setReplayedBalance(BigDecimal replayedBalance) {
        this.replayedBalance = replayedBalance;
    }

    public BigDecimal getDiscrepancy() {
        return discrepancy;
    }

    public void setDiscrepancy(BigDecimal discrepancy) {
        this.discrepancy = discrepancy;
    }
}
