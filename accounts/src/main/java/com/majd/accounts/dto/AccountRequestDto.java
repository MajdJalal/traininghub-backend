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
    private String phoneNumber;
    private String role;
}
