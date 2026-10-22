package com.akhil.paymoneytoday.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.paymoneytoday.enums.EntryType;
import com.akhil.paymoneytoday.models.Ledger;
import com.akhil.paymoneytoday.repo.LedgerRepository;

@Service
public class LedgerService {
    LedgerRepository ledgerRepository;

    @Autowired
    public LedgerService(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    public boolean checkLedgerAudit(Long id) {
        List<Ledger> ledgers = ledgerRepository.findByWalletId(id);
        BigDecimal credit = BigDecimal.ZERO;
        BigDecimal debit = BigDecimal.ZERO;
        for (Ledger l : ledgers) {
            if (l.getEntryType() == EntryType.DEBIT)
                debit = debit.add(l.getAmount());
            else
                credit = credit.add(l.getAmount());
        }
        return credit.compareTo(debit) == 0;
    }
}
