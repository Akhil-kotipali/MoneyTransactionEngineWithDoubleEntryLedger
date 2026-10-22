package com.akhil.paymoneytoday.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akhil.paymoneytoday.dto.wallet.AddBalanceDTO;
import com.akhil.paymoneytoday.dto.wallet.BalanceResponseDTO;
import com.akhil.paymoneytoday.dto.wallet.TransferMoneyDTO;
import com.akhil.paymoneytoday.dto.wallet.TransferMoneyResponseDTO;
import com.akhil.paymoneytoday.models.Transaction;
import com.akhil.paymoneytoday.service.TransactionService;
import com.akhil.paymoneytoday.service.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    WalletService walletService;
    TransactionService transactionService;

    public WalletController(WalletService walletService, TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @PostMapping("/addMoney")
    public BalanceResponseDTO addMoneyToWallet(@RequestBody AddBalanceDTO req) {
        return walletService.addMoneyToWallet(req);
    }

    @PostMapping("/transferMoney")
    public TransferMoneyResponseDTO transferMoney(@RequestBody TransferMoneyDTO req) {
        return walletService.transferMoney(req);
    }

    @GetMapping("/{id}/balance")
    public BigDecimal getWalletBalance(@PathVariable Long id) {
        return walletService.getBalance(id);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<Page<Transaction>> getWalletTransactions(
            @PathVariable("id") Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Transaction> transactions = transactionService.getTransactionList(id, page, size);
        return ResponseEntity.ok(transactions);
    }
}
