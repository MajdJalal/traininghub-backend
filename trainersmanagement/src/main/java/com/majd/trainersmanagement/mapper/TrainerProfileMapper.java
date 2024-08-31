package com.majd.trainersmanagement.mapper;

import com.majd.trainersmanagement.dto.TrainerProfileRequestDto;
import com.majd.trainersmanagement.dto.TrainerProfileResponseDto;
import com.majd.trainersmanagement.entity.TrainerProfile;
import org.springframework.stereotype.Component;

@Component
public class TrainerProfileMapper {


//    private Long id;
//    private String firstName;
//    private String lastName;
//    private String nickName;
//    private String email;//this email that is gonna be shown on the website so trainees contact u with
//    private String emailAccount;// the email of the account having this profile, gonna be inserted directly when creating a new profile
//    private String phoneNumber;
//    private LocalDate birthDay;
//    private String country;
    public TrainerProfile toTrainerProfile(TrainerProfileRequestDto trainerProfileRequestDto, String emailAccount) {
        return TrainerProfile.builder()
                .firstName(trainerProfileRequestDto.getFirstName())
                .lastName(trainerProfileRequestDto.getLastName())
                .nickName(trainerProfileRequestDto.getNickName())
                .email(trainerProfileRequestDto.getEmail())
                .emailAccount(emailAccount)
                .phoneNumber(trainerProfileRequestDto.getPhoneNumber())
                .birthDay(trainerProfileRequestDto.getBirthDay())
                .country(trainerProfileRequestDto.getCountry())
                .build();
    }


    public TrainerProfileResponseDto toTrainerProfileResponseDto(TrainerProfile trainerProfile) {
        return TrainerProfileResponseDto.builder()
                .firstName(trainerProfile.getFirstName())
                .lastName(trainerProfile.getLastName())
                .nickName(trainerProfile.getNickName())
                .email(trainerProfile.getEmail())
                .emailAccount(trainerProfile.getEmailAccount())
                .phoneNumber(trainerProfile.getPhoneNumber())
                .birthDay(trainerProfile.getBirthDay())
                .country(trainerProfile.getCountry())
                .cv(trainerProfile.getCv())
                .gyms(trainerProfile.getGyms())
                .build();
    }
}
