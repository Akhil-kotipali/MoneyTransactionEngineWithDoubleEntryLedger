package com.akhil.paymoneytoday.dto.account;

import com.akhil.paymoneytoday.enums.AccountStatus;

public class UserUpdateDTO extends UserCreateDTO {

    private AccountStatus status;

    public UserUpdateDTO() {
        super();
    }

    public UserUpdateDTO(String name, String password, String email) {
        super(name, password, email);
    }

    public UserUpdateDTO(String name, String password, String email, AccountStatus status) {
        super(name, password, email);
        this.status = status;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
