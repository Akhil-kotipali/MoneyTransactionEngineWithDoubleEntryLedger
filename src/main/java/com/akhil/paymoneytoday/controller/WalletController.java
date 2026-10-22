package com.akhil.paymoneytoday.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akhil.paymoneytoday.dto.wallet.AddBalanceDTO;
import com.akhil.paymoneytoday.dto.wallet.BalanceResponseDTO;
import com.akhil.paymoneytoday.dto.wallet.TransferMoneyDTO;
import com.akhil.paymoneytoday.dto.wallet.TransferMoneyResponseDTO;
import com.akhil.paymoneytoday.service.TransactionsService;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private TransactionsService transactionsService;

    @PostMapping("/addMoney")
    public BalanceResponseDTO addMoney(@RequestBody AddBalanceDTO request) throws Exception {
        return transactionsService.addMoney(request);
    }

    @PostMapping("/transferMoney")
    public TransferMoneyResponseDTO transferMoney(@RequestBody TransferMoneyDTO request) throws Exception {
        return transactionsService.transferMoney(request);
    }

    @GetMapping("/{walletId}/balance")
    public Map<String, Object> getBalance(@PathVariable Long walletId) throws Exception {
        BigDecimal balance = transactionsService.getCachedOrDbBalance(walletId);
        return Map.of("walletId", walletId, "balance", balance != null ? balance : BigDecimal.ZERO);
    }
}
