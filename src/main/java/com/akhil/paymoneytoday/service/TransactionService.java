package com.akhil.paymoneytoday.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.akhil.paymoneytoday.enums.TransactionStatus;
import com.akhil.paymoneytoday.models.Transaction;
import com.akhil.paymoneytoday.repo.TransactionRepository;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Long senderWalletId, Long receiverWalletId, BigDecimal amount,
            BigDecimal senderBalanceBefore, BigDecimal senderBalanceAfter,
            BigDecimal receiverBalanceBefore, BigDecimal receiverBalanceAfter,
            TransactionStatus status, String idempotencyKey) {

        return transactionRepository.save(new Transaction(senderWalletId, receiverWalletId, amount,
                senderBalanceBefore, senderBalanceAfter,
                receiverBalanceBefore, receiverBalanceAfter,
                status, idempotencyKey));
    }

    public Page<Transaction> getAllTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return transactionRepository.findAll(pageable);
    }

    public Page<Transaction> getTransactionsByWallet(Long walletId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return transactionRepository.findBySenderWalletIdOrReceiverWalletId(walletId, walletId, pageable);
    }
}
