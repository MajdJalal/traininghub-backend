package com.majd.traineesmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class TraineeProfileRequestDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    private String nickName;

    @Email
    private String email;
    //the phone num must be nine digits, i get the country code from the country
    @Pattern(regexp = "^\\d{9}$", message = "Invalid phone number")
    private String phoneNumber;
    private LocalDate birthDay;
    private String country;
}
