package com.akhil.paymoneytoday.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akhil.paymoneytoday.dto.wallet.AddBalanceDTO;
import com.akhil.paymoneytoday.dto.wallet.BalanceResponseDTO;
import com.akhil.paymoneytoday.dto.wallet.TransferMoneyDTO;
import com.akhil.paymoneytoday.dto.wallet.TransferMoneyResponseDTO;
import com.akhil.paymoneytoday.enums.EntryType;
import com.akhil.paymoneytoday.enums.TransactionStatus;
import com.akhil.paymoneytoday.models.Account;
import com.akhil.paymoneytoday.models.Transaction;
import com.akhil.paymoneytoday.models.Wallet;
import com.akhil.paymoneytoday.repo.AccountRepository;
import com.akhil.paymoneytoday.repo.LedgerRepository;
import com.akhil.paymoneytoday.repo.TransactionRepository;
import com.akhil.paymoneytoday.repo.WalletRepository;

@Service
public class TransactionsService {
    private final AccountRepository accountRepository;
    private final WalletRepository walletRepository;
    private final LedgerRepository ledgerRepository;
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final LedgerService ledgerService;
    private final TransactionService transactionService;
    private final RedisService redisService;

    public TransactionsService(
            AccountRepository accountRepository,
            WalletRepository walletRepository,
            LedgerRepository ledgerRepository,
            TransactionRepository transactionRepository,
            UserService userService,
            LedgerService ledgerService,
            TransactionService transactionService,
            RedisService redisService) {
        this.accountRepository = accountRepository;
        this.walletRepository = walletRepository;
        this.ledgerRepository = ledgerRepository;
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.ledgerService = ledgerService;
        this.transactionService = transactionService;
        this.redisService = redisService;
    }

    public BigDecimal getCachedOrDbBalance(Long walletId) throws Exception {
        return redisService.getBalance(walletId).orElseGet(() -> {
            Wallet wallet = walletRepository.findById(walletId).orElse(null);
            if (wallet == null) {
                return null;
            }
            redisService.setBalance(wallet.getId(), wallet.getBalance());
            return wallet.getBalance();
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public BalanceResponseDTO addMoney(AddBalanceDTO request) throws Exception {
        BigDecimal amount = request.getAmount();
        Long walletId = request.getWalletId();
        String username = request.getUsername();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Invalid deposit amount");
        }

        if (request.getIdempotencyKey() != null && !request.getIdempotencyKey().isBlank()) {
            boolean acquired = redisService.checkAndAcquireIdempotency(request.getIdempotencyKey());
            if (!acquired) {
                throw new Exception("Duplicate transaction detected with idempotency key: " + request.getIdempotencyKey());
            }
        }

        Wallet wallet = walletRepository.findByIdForUpdate(walletId).orElse(null);
        if (wallet == null) {
            throw new Exception("Wallet not found");
        }

        if (username != null && !username.isBlank()) {
            Account account = accountRepository.findById(wallet.getAccountId()).orElse(null);
            if (account == null || !account.getName().equalsIgnoreCase(username.trim())) {
                throw new Exception("Invalid credentials: user does not own this wallet");
            }
        }

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        redisService.setBalance(wallet.getId(), wallet.getBalance());

        return new BalanceResponseDTO(wallet.getId(), username, wallet.getBalance(), wallet.getCurrency());
    }

    @Transactional(rollbackFor = Exception.class)
    public TransferMoneyResponseDTO transferMoney(TransferMoneyDTO request) throws Exception {
        if (request.getUsername() == null || request.getToReceiverUsername() == null) {
            throw new Exception("Sender and receiver usernames are required");
        }
        if (request.getUsername().equalsIgnoreCase(request.getToReceiverUsername())) {
            throw new Exception("Cannot transfer money to the same account");
        }
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Transfer amount must be greater than zero");
        }

        if (request.getIdempotencyKey() != null && !request.getIdempotencyKey().isBlank()) {
            boolean acquired = redisService.checkAndAcquireIdempotency(request.getIdempotencyKey());
            if (!acquired) {
                throw new Exception("Duplicate transaction detected with idempotency key: " + request.getIdempotencyKey());
            }
        }

        Account senderAccount = accountRepository.findByName(request.getUsername()).orElse(null);
        Account receiverAccount = accountRepository.findByName(request.getToReceiverUsername()).orElse(null);

        if (senderAccount == null || receiverAccount == null) {
            throw new Exception("Account not found");
        }

        Wallet senderWalletRef = walletRepository.findByAccountId(senderAccount.getId()).orElse(null);
        Wallet receiverWalletRef = walletRepository.findByAccountId(receiverAccount.getId()).orElse(null);

        if (senderWalletRef == null || receiverWalletRef == null) {
            throw new Exception("Wallet not found");
        }

        if (senderWalletRef.getId().equals(receiverWalletRef.getId())) {
            throw new Exception("Cannot transfer to the same wallet");
        }

        Long firstId = Math.min(senderWalletRef.getId(), receiverWalletRef.getId());
        Long secondId = Math.max(senderWalletRef.getId(), receiverWalletRef.getId());

        Wallet firstLocked = walletRepository.findByIdForUpdate(firstId)
                .orElseThrow(() -> new Exception("Wallet not found with id: " + firstId));
        Wallet secondLocked = walletRepository.findByIdForUpdate(secondId)
                .orElseThrow(() -> new Exception("Wallet not found with id: " + secondId));

        Wallet senderWallet = senderWalletRef.getId().equals(firstId) ? firstLocked : secondLocked;
        Wallet receiverWallet = receiverWalletRef.getId().equals(firstId) ? firstLocked : secondLocked;

        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new Exception("Insufficient balance");
        }

        BigDecimal senderBalanceBefore = senderWallet.getBalance();
        BigDecimal receiverBalanceBefore = receiverWallet.getBalance();

        senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmount()));
        receiverWallet.setBalance(receiverWallet.getBalance().add(request.getAmount()));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        redisService.setBalance(senderWallet.getId(), senderWallet.getBalance());
        redisService.setBalance(receiverWallet.getId(), receiverWallet.getBalance());

        Transaction currentTransaction = transactionService.createTransaction(senderWallet.getId(),
                receiverWallet.getId(), request.getAmount(), senderBalanceBefore,
                senderWallet.getBalance(), receiverBalanceBefore, receiverWallet.getBalance(),
                TransactionStatus.PENDING, request.getIdempotencyKey());

        ledgerService.recordTransaction(currentTransaction.getId(), senderWallet.getId(), EntryType.DEBIT,
                request.getAmount());
        ledgerService.recordTransaction(currentTransaction.getId(), receiverWallet.getId(), EntryType.CREDIT,
                request.getAmount());

        return new TransferMoneyResponseDTO(request.getUsername(), request.getToReceiverUsername(), request.getAmount(),
                senderWallet.getBalance(), currentTransaction.getCreatedAt());
    }
}
