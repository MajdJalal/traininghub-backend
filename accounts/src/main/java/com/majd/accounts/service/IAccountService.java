package com.majd.accounts.service;

import com.majd.accounts.dto.AccountRequestDto;
import jakarta.validation.constraints.Email;

public interface IAccountService {
    void createAccount(AccountRequestDto accountRequestDto);

//    String login(AccountRequestDto accountRequestDto);
}
