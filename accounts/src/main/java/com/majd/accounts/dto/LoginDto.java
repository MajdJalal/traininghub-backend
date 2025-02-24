package com.majd.accounts.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class LoginDto {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
