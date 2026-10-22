package com.akhil.paymoneytoday.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.akhil.paymoneytoday.dto.account.UserCreateDTO;
import com.akhil.paymoneytoday.dto.account.UserResponseDTO;
import com.akhil.paymoneytoday.enums.AccountStatus;
import com.akhil.paymoneytoday.models.Account;
import com.akhil.paymoneytoday.models.Wallet;
import com.akhil.paymoneytoday.repo.AccountRepository;
import com.akhil.paymoneytoday.repo.WalletRepository;

@Service
public class UserService {
    public AccountRepository accountRepository;
    public WalletRepository walletRepository;

    @Autowired
    public UserService(AccountRepository accountRepository, WalletRepository walletRepository) {
        this.accountRepository = accountRepository;
        this.walletRepository = walletRepository;
    }

    public UserResponseDTO createUser(UserCreateDTO req) {
        if (req == null) {
            throw new IllegalArgumentException("User details cannot be null");
        }

        if (req.getName() == null || req.getName().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (req.getEmail() == null || req.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (req.getPassword() == null || req.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        Account accountOfCurrentUser = new Account(req.getName().trim(), req.getPassword(), req.getEmail().trim(),
                LocalDateTime.now(), AccountStatus.ACTIVE);
        if (accountRepository.existsByName(req.getName().trim())) {
            throw new IllegalArgumentException("Username is already taken: " + req.getName().trim());
        }

        if (accountRepository.existsByEmail(req.getEmail().trim())) {
            throw new IllegalArgumentException("Email is already registered: " + req.getEmail().trim());
        }
        accountRepository.save(accountOfCurrentUser);
        Wallet wallet = new Wallet(accountOfCurrentUser.getId(), BigDecimal.ZERO, "INR");
        walletRepository.save(wallet);
        return new UserResponseDTO(accountOfCurrentUser.getName(), accountOfCurrentUser.getEmail(), LocalDateTime.now(),
                AccountStatus.ACTIVE);
    }

    public UserResponseDTO getUserById(Long id) {

        if (id != null) {

            // if (name != null && !name.isBlank()) {
            // return getUserByName(name);
            // }
            // if (email != null && !email.isBlank()) {
            // return getUserByEmail(email);
            // }

            Account account = accountRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("User not found"));
            return new UserResponseDTO(account.getName(), account.getEmail(), account.getCreatedAt(),
                    account.getStatus());
        }
        return new UserResponseDTO("null", "null", null, null);
    }

    public UserResponseDTO getUserByName(String name) {

        if (name != null && !name.isBlank()) {

            Account account = accountRepository.findByName(name).orElseThrow(
                    () -> new IllegalArgumentException("User not found"));
            return new UserResponseDTO(account.getName(), account.getEmail(), account.getCreatedAt(),
                    account.getStatus());
        }
        return new UserResponseDTO("null", "null", null, null);
    }

    public UserResponseDTO getUserByEmail(String email) {

        if (email != null && !email.isBlank()) {

            Account account = accountRepository.findByEmail(email).orElseThrow(
                    () -> new IllegalArgumentException("User not found"));
            return new UserResponseDTO(account.getName(), account.getEmail(), account.getCreatedAt(),
                    account.getStatus());
        }
        return new UserResponseDTO("null", "null", null, null);

    }
}
