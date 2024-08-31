package com.majd.trainersmanagement.dto;

import com.majd.trainersmanagement.entity.CV;
import com.majd.trainersmanagement.entity.Gym;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;


@Builder
@Getter
public class TrainerProfileResponseDto {

    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private String emailAccount;
    private String phoneNumber;
    private LocalDate birthDay;
    private String country;

    private Set<Gym> gyms;

    private CV cv;
}
