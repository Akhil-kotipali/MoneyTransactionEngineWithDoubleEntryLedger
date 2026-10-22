package com.akhil.paymoneytoday.repo;

import com.akhil.paymoneytoday.models.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {
    List<Ledger> findByTransactionId(Long transactionId);
    List<Ledger> findByWalletId(Long walletId);
    List<Ledger> findByUserId(Long id);
    
    @Query("SELECT COALESCE(SUM(CASE WHEN l.entryType = com.akhil.paymoneytoday.enums.EntryType.CREDIT THEN l.amount ELSE -l.amount END), 0) FROM Ledger l")
    BigDecimal calculateGlobalZeroSum();

    @Query("SELECT l.walletId, COALESCE(SUM(CASE WHEN l.entryType = com.akhil.paymoneytoday.enums.EntryType.CREDIT THEN l.amount ELSE -l.amount END), 0) FROM Ledger l GROUP BY l.walletId")
    List<Object[]> calculateLedgerBalancePerWallet();

    @Query("SELECT COALESCE(SUM(CASE WHEN l.entryType = com.akhil.paymoneytoday.enums.EntryType.CREDIT THEN l.amount ELSE -l.amount END), 0) FROM Ledger l WHERE l.walletId = :walletId")
    BigDecimal calculateReplayedBalanceForWallet(@Param("walletId") Long walletId);
}
