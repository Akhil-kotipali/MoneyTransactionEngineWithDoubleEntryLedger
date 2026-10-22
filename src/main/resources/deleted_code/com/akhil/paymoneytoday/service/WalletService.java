package com.akhil.paymoneytoday.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.paymoneytoday.dto.wallet.AddBalanceDTO;
import com.akhil.paymoneytoday.dto.wallet.BalanceResponseDTO;
import com.akhil.paymoneytoday.dto.wallet.TransferMoneyDTO;
import com.akhil.paymoneytoday.dto.wallet.TransferMoneyResponseDTO;
import com.akhil.paymoneytoday.enums.EntryType;
import com.akhil.paymoneytoday.enums.TransactionStatus;
import com.akhil.paymoneytoday.models.Account;
import com.akhil.paymoneytoday.models.Ledger;
import com.akhil.paymoneytoday.models.Transaction;
import com.akhil.paymoneytoday.models.Wallet;
import com.akhil.paymoneytoday.repo.AccountRepository;
import com.akhil.paymoneytoday.repo.LedgerRepository;
import com.akhil.paymoneytoday.repo.TransactionRepository;
import com.akhil.paymoneytoday.repo.WalletRepository;

import jakarta.transaction.Transactional;

@Service
public class WalletService {
    public AccountRepository accountRepository;
    public WalletRepository walletRepository;
    public TransactionRepository transactionRepository;
    public LedgerRepository ledgerRepository;
    public IdempotencyService idempotencyService;

    @Autowired
    public WalletService(AccountRepository accountRepository, WalletRepository walletRepository,
            TransactionRepository transactionRepository, LedgerRepository ledgerRepository,
            IdempotencyService idempotencyService) {
        this.accountRepository = accountRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.ledgerRepository = ledgerRepository;
        this.idempotencyService = idempotencyService;
    }

    @Transactional
    public BalanceResponseDTO addMoneyToWallet(AddBalanceDTO req) {
        if (req == null || req.getWalletId() == null || req.getAmount() == null
                || req.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new BalanceResponseDTO(null, null, "null", null, "null");
        }

        Wallet wallet = walletRepository.findById(req.getWalletId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal newBalance = currentBalance.add(req.getAmount());

        wallet.setBalance(newBalance);
        Transaction transaction = new Transaction(req.getWalletId(), req.getWalletId(), req.getAmount(),
                currentBalance, newBalance, null, null, TransactionStatus.COMPLETED);
        walletRepository.save(wallet);
        Long transactionIdTemp = transactionRepository.save(transaction).getId();

        Ledger ledger = new Ledger(transactionIdTemp, req.getWalletId(), EntryType.CREDIT, req.getAmount());
        ledgerRepository.save(ledger);

        return new BalanceResponseDTO(wallet.getId(), wallet.getAccountId(), null, wallet.getBalance(),
                wallet.getCurrency());
    }

    @Transactional
    public TransferMoneyResponseDTO transferMoney(TransferMoneyDTO req) {
        if (req == null || req.getUsername() == null || req.getToReceiverUsername() == null
                || req.getAmount() == null) {
            return new TransferMoneyResponseDTO("null", "null", null, null, null);
        }

        if (req.getUsername().equals(req.getToReceiverUsername())) {
            return new TransferMoneyResponseDTO("null", "null", null, null, null);
        }

        if (req.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        if (req.getIdempotencyKey() != null && !req.getIdempotencyKey().isBlank()) {
            Optional<Transaction> existing = idempotencyService.checkKey(req.getIdempotencyKey());
            if (existing.isPresent()) {
                Transaction tx = existing.get();
                return new TransferMoneyResponseDTO(req.getUsername(), req.getToReceiverUsername(),
                        tx.getAmount(), tx.getSenderBalanceAfter(), tx.getCreatedAt());
            }
        }

        Account sender = accountRepository.findByName(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Account receiver = accountRepository.findByName(req.getToReceiverUsername())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Wallet sWallet = walletRepository.findByAccountId(sender.getId())
                .orElseThrow(() -> new RuntimeException("sender wallet not found"));
        Wallet rWallet = walletRepository.findByAccountId(receiver.getId())
                .orElseThrow(() -> new RuntimeException("receiver wallet not found"));

        Long firstId = Math.min(sWallet.getId(), rWallet.getId());
        Long secondId = Math.max(sWallet.getId(), rWallet.getId());

        Wallet firstLocked = walletRepository.findByIdForUpdate(firstId)
                .orElseThrow(() -> new RuntimeException("Wallet not found: " + firstId));
        Wallet secondLocked = walletRepository.findByIdForUpdate(secondId)
                .orElseThrow(() -> new RuntimeException("Wallet not found: " + secondId));

        Wallet senderWallet = (sWallet.getId().equals(firstLocked.getId())) ? firstLocked : secondLocked;
        Wallet receiverWallet = (rWallet.getId().equals(firstLocked.getId())) ? firstLocked : secondLocked;

        BigDecimal currentSenderBalance = senderWallet.getBalance();

        if (currentSenderBalance.compareTo(req.getAmount()) < 0) {
            throw new RuntimeException(" insufficient balance");
        }

        senderWallet.setBalance(currentSenderBalance.subtract(req.getAmount()));
        BigDecimal currentRecieverBalance = receiverWallet.getBalance();

        receiverWallet.setBalance(currentRecieverBalance.add(req.getAmount()));
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transaction transaction = new Transaction(senderWallet.getId(), receiverWallet.getId(),
                req.getAmount(),
                currentSenderBalance, senderWallet.getBalance(), currentRecieverBalance,
                receiverWallet.getBalance(),
                TransactionStatus.COMPLETED);

        if (req.getIdempotencyKey() != null && !req.getIdempotencyKey().isBlank()) {
            transaction.setIdempotencyKey(req.getIdempotencyKey().trim());
        }

        Long transactionId = transactionRepository.save(transaction).getId();

        Ledger senderLedger = new Ledger(transactionId, senderWallet.getId(), EntryType.DEBIT,
                req.getAmount());
        Ledger receiverLedger = new Ledger(transactionId, receiverWallet.getId(), EntryType.CREDIT,
                req.getAmount());
        ledgerRepository.save(senderLedger);
        ledgerRepository.save(receiverLedger);

        return new TransferMoneyResponseDTO(sender.getName(), receiver.getName(), req.getAmount(),
                senderWallet.getBalance(), LocalDateTime.now());
    }

    public BigDecimal getBalance(Long id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new RuntimeException("Wallet not found"));
        return wallet.getBalance();
    }
}
