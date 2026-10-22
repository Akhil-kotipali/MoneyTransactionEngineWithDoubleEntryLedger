package com.akhil.paymoneytoday.dto.ledger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReconciliationResponseDTO {

    private boolean reconciled;
    private BigDecimal globalZeroSum;
    private boolean globalZeroSumValid;
    private int totalWalletsAudited;
    private int discrepancyCount;
    private List<WalletDiscrepancyDTO> discrepancies;
    private LocalDateTime auditedAt;

    public ReconciliationResponseDTO() {
    }

    public ReconciliationResponseDTO(boolean reconciled, BigDecimal globalZeroSum, boolean globalZeroSumValid,
                                     int totalWalletsAudited, int discrepancyCount,
                                     List<WalletDiscrepancyDTO> discrepancies, LocalDateTime auditedAt) {
        this.reconciled = reconciled;
        this.globalZeroSum = globalZeroSum;
        this.globalZeroSumValid = globalZeroSumValid;
        this.totalWalletsAudited = totalWalletsAudited;
        this.discrepancyCount = discrepancyCount;
        this.discrepancies = discrepancies;
        this.auditedAt = auditedAt;
    }

    public boolean isReconciled() {
        return reconciled;
    }

    public void setReconciled(boolean reconciled) {
        this.reconciled = reconciled;
    }

    public BigDecimal getGlobalZeroSum() {
        return globalZeroSum;
    }

    public void setGlobalZeroSum(BigDecimal globalZeroSum) {
        this.globalZeroSum = globalZeroSum;
    }

    public boolean isGlobalZeroSumValid() {
        return globalZeroSumValid;
    }

    public void setGlobalZeroSumValid(boolean globalZeroSumValid) {
        this.globalZeroSumValid = globalZeroSumValid;
    }

    public int getTotalWalletsAudited() {
        return totalWalletsAudited;
    }

    public void setTotalWalletsAudited(int totalWalletsAudited) {
        this.totalWalletsAudited = totalWalletsAudited;
    }

    public int getDiscrepancyCount() {
        return discrepancyCount;
    }

    public void setDiscrepancyCount(int discrepancyCount) {
        this.discrepancyCount = discrepancyCount;
    }

    public List<WalletDiscrepancyDTO> getDiscrepancies() {
        return discrepancies;
    }

    public void setDiscrepancies(List<WalletDiscrepancyDTO> discrepancies) {
        this.discrepancies = discrepancies;
    }

    public LocalDateTime getAuditedAt() {
        return auditedAt;
    }

    public void setAuditedAt(LocalDateTime auditedAt) {
        this.auditedAt = auditedAt;
    }
}
