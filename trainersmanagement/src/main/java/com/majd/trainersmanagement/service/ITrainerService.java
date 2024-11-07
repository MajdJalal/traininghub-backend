package com.majd.trainersmanagement.service;

import com.majd.trainersmanagement.dto.TrainerProfileRequestDto;
import com.majd.trainersmanagement.dto.TrainerProfileResponseDto;

import java.util.List;

public interface ITrainerService {
    void createTrainerProfile(TrainerProfileRequestDto trainerProfileRequestDto, String email_account);

    TrainerProfileResponseDto getTrainerProfile(String emailAccount);

    List<TrainerProfileResponseDto> getTrainerProfiles();
}
