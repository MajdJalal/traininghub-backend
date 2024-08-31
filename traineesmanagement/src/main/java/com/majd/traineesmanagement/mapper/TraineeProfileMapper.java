package com.majd.traineesmanagement.mapper;


import com.majd.traineesmanagement.dto.TraineeProfileRequestDto;
import com.majd.traineesmanagement.dto.TraineeProfileResponseDto;
import com.majd.traineesmanagement.entity.TraineeProfile;
import org.springframework.stereotype.Component;

@Component
public class TraineeProfileMapper {
    public TraineeProfile toTraineeProfile(TraineeProfileRequestDto traineeProfileRequestDto, String emailAccount) {
            return TraineeProfile.builder()
                    .firstName(traineeProfileRequestDto.getFirstName())
                    .lastName(traineeProfileRequestDto.getLastName())
                    .nickName(traineeProfileRequestDto.getNickName())
                    .email(traineeProfileRequestDto.getEmail())
                    .emailAccount(emailAccount)
                    .phoneNumber(traineeProfileRequestDto.getPhoneNumber())
                    .birthDay(traineeProfileRequestDto.getBirthDay())
                    .country(traineeProfileRequestDto.getCountry())
                    .build();
    }

    public TraineeProfileResponseDto toTraineeProfileResponseDto(TraineeProfile traineeProfile) {
        return TraineeProfileResponseDto.builder()
                .firstName(traineeProfile.getFirstName())
                .lastName(traineeProfile.getLastName())
                .nickName(traineeProfile.getNickName())
                .email(traineeProfile.getEmail())
                .emailAccount(traineeProfile.getEmailAccount())
                .phoneNumber(traineeProfile.getPhoneNumber())
                .birthDay(traineeProfile.getBirthDay())
                .country(traineeProfile.getCountry())
                .build();
    }
}
