package com.majd.accounts.service;

import com.majd.accounts.dto.AccountRequestDto;
import com.majd.accounts.model.AccountModel;
import jakarta.validation.constraints.Email;

import java.util.List;

public interface IAccountService {
    void createAccount(AccountRequestDto accountRequestDto);

    List<AccountModel> getAccounts();

//    String login(AccountRequestDto accountRequestDto);
}
