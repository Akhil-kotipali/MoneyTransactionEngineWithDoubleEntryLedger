package com.akhil.paymoneytoday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akhil.paymoneytoday.models.Transaction;
import com.akhil.paymoneytoday.repo.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public Page<Transaction> getTransactionList(Long walletId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findBySenderWalletIdOrReceiverWalletId(walletId, walletId, pageable);
    }
}
