package com.majd.traineesmanagement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Builder
@Getter
public class TraineeProfileResponseDto {
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private String emailAccount;
    private String phoneNumber;
    private LocalDate birthDay;
    private String country;

}
