package com.akhil.paymoneytoday.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akhil.paymoneytoday.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);

    Page<Transaction> findBySenderWalletIdOrReceiverWalletId(Long senderWalletId, Long receiverWalletId, Pageable pageable);
}
