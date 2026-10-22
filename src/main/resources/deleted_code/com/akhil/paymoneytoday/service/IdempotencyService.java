package com.akhil.paymoneytoday.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.akhil.paymoneytoday.models.Transaction;
import com.akhil.paymoneytoday.repo.TransactionRepository;

@Service
public class IdempotencyService {

    private final TransactionRepository transactionRepository;

    public IdempotencyService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Optional<Transaction> checkKey(String key) {
        if (key == null || key.isBlank()) {
            return Optional.empty();
        }
        return transactionRepository.findByIdempotencyKey(key.trim());
    }
}
