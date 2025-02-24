package com.majd.accounts.service;

import com.majd.accounts.dto.AccountRequestDto;
import com.majd.accounts.dto.LoginDto;
import com.majd.accounts.model.AccountModel;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAccountService {
    boolean createAccount(AccountRequestDto accountRequestDto);

    List<AccountModel> getAccounts();

    String login(LoginDto loginDto) throws Exception;

//    String login(AccountRequestDto accountRequestDto);
}
