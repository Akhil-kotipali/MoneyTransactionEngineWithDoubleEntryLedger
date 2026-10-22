package com.akhil.paymoneytoday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akhil.paymoneytoday.models.Transaction;
import com.akhil.paymoneytoday.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public Page<Transaction> getAllTransactions(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return transactionService.getAllTransactions(page, size);
    }

    @GetMapping("/wallet/{walletId}")
    public Page<Transaction> getTransactionsByWallet(
            @PathVariable("walletId") Long walletId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return transactionService.getTransactionsByWallet(walletId, page, size);
    }
}
