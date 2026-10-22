package com.akhil.paymoneytoday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akhil.paymoneytoday.service.LedgerService;

@RestController
@RequestMapping("/ledger")
public class LedgerController {
    @Autowired
    LedgerService ledgerService;

    @GetMapping("/audit/{id}")
    public boolean checkLedgerAudit(@PathVariable Long id) {
        return ledgerService.checkLedgerAudit(id);
    }
}
