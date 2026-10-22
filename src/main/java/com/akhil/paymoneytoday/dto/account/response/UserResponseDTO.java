package com.akhil.paymoneytoday.dto.account.response;

import com.akhil.paymoneytoday.enums.AccountStatus;
import java.time.LocalDateTime;

public class UserResponseDTO {

    private String name;
    private String email;
    private LocalDateTime createdAt;
    private AccountStatus status;

    public UserResponseDTO() {
    }

    public UserResponseDTO(String name, String email, LocalDateTime createdAt, AccountStatus status) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.status = status;
    }

    public static UserResponseDTO fromAccount(com.akhil.paymoneytoday.models.Account account) {
        if (account == null) {
            return null;
        }
        return new UserResponseDTO(

                account.getName(),
                account.getEmail(),
                account.getCreatedAt(),
                account.getStatus());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
