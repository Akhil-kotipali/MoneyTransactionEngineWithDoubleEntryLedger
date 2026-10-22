package com.akhil.paymoneytoday.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.akhil.paymoneytoday.enums.EntryType;
import com.akhil.paymoneytoday.models.Ledger;
import com.akhil.paymoneytoday.repo.AccountRepository;
import com.akhil.paymoneytoday.repo.LedgerRepository;
import com.akhil.paymoneytoday.repo.WalletRepository;

@Service
public class LedgerService {
    AccountRepository accountRepository;
    WalletRepository walletRepository;
    LedgerRepository ledgerRepository;

    public LedgerService(AccountRepository accountRepository,
            WalletRepository walletRepository,
            LedgerRepository ledgerRepository) {
        this.accountRepository = accountRepository;
        this.walletRepository = walletRepository;
        this.ledgerRepository = ledgerRepository;
    }

    public void recordTransaction(Long transactionId, Long walletId, EntryType entryType, BigDecimal amount) {
        Ledger ledger = new Ledger(transactionId, walletId, entryType, amount);
        ledgerRepository.save(ledger);
    }

    public Page<Ledger> getAllLedgers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ledgerRepository.findAll(pageable);
    }

    public Page<Ledger> getLedgersByWallet(Long walletId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ledgerRepository.findByWalletId(walletId, pageable);
    }

    public boolean checkLedgerAudit(Long id) {
        List<Ledger> ledgers = ledgerRepository.findByWalletId(id);
        BigDecimal credit = BigDecimal.ZERO;
        BigDecimal debit = BigDecimal.ZERO;
        for (Ledger l : ledgers) {
            if (l.getEntryType() == EntryType.DEBIT) {
                debit = debit.add(l.getAmount());
            } else {
                credit = credit.add(l.getAmount());
            }
        }
        return credit.compareTo(debit) == 0;
    }

    public BigDecimal getGlobalZeroSum() {
        return ledgerRepository.calculateGlobalZeroSum();
    }
}
