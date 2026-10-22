package com.akhil.paymoneytoday.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.akhil.paymoneytoday.dto.account.request.UserCreateDTO;
import com.akhil.paymoneytoday.dto.account.response.UserResponseDTO;
import com.akhil.paymoneytoday.enums.AccountStatus;
import com.akhil.paymoneytoday.models.Account;
import com.akhil.paymoneytoday.models.Wallet;
import com.akhil.paymoneytoday.repo.AccountRepository;
import com.akhil.paymoneytoday.repo.WalletRepository;

@Service
public class UserService {
    private final AccountRepository accountRepository;
    private final WalletRepository walletRepository;

    public UserService(AccountRepository accountRepository, WalletRepository walletRepository) {
        this.accountRepository = accountRepository;
        this.walletRepository = walletRepository;
    }

    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        Account userAccount = new Account(userCreateDTO.getName(), userCreateDTO.getPassword(),
                userCreateDTO.getEmail(), LocalDateTime.now(), AccountStatus.ACTIVE);
        accountRepository.save(userAccount);

        Wallet userWallet = new Wallet(userAccount.getId(), BigDecimal.ZERO, "INR");
        walletRepository.save(userWallet);

        return new UserResponseDTO(userCreateDTO.getName(), userCreateDTO.getEmail(), LocalDateTime.now(),
                AccountStatus.ACTIVE);
    }

    public UserResponseDTO readUser(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return null;
        }
        return new UserResponseDTO(account.getName(), account.getEmail(), account.getCreatedAt(), account.getStatus());
    }

    public UserResponseDTO updateUser(Long id, UserCreateDTO userCreateDTO) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return null;
        }
        account.setName(userCreateDTO.getName());
        account.setEmail(userCreateDTO.getEmail());
        accountRepository.save(account);
        return new UserResponseDTO(account.getName(), account.getEmail(), account.getCreatedAt(), account.getStatus());
    }

    public UserResponseDTO deleteUser(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return null;
        }
        account.setStatus(AccountStatus.INACTIVE);
        accountRepository.save(account);
        return new UserResponseDTO(account.getName(), account.getEmail(), account.getCreatedAt(), account.getStatus());
    }

    public boolean checkUserCredentials(Long id, String password) {
        if (accountRepository.existsById(id)) {
            Account account = accountRepository.findById(id).get();
            if (account.getHashedPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
