package com.akhil.paymoneytoday.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akhil.paymoneytoday.models.Ledger;
import com.akhil.paymoneytoday.service.LedgerService;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    LedgerService ledgerService;

    @GetMapping
    public Page<Ledger> getAllLedgers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ledgerService.getAllLedgers(page, size);
    }

    @GetMapping("/wallet/{walletId}")
    public Page<Ledger> getLedgersByWallet(
            @PathVariable("walletId") Long walletId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ledgerService.getLedgersByWallet(walletId, page, size);
    }

    @GetMapping("/audit/{id}")
    public boolean checkLedgerAudit(@PathVariable("id") Long id) {
        return ledgerService.checkLedgerAudit(id);
    }

    @GetMapping("/globalZeroSum")
    public BigDecimal getGlobalZeroSum() {
        return ledgerService.getGlobalZeroSum();
    }
}
