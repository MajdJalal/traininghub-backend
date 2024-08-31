package com.majd.accounts.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class AccountRequestDto {

    @NotBlank
    private String name;


    @NotBlank
    private String email;

    @NotBlank
    private String password;//stored hashed
    //TO-DO , put a constraint (pattern) on the phoneNumber format
    private String phoneNumber;
}
