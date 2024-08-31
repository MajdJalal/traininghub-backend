package com.majd.trainersmanagement.service;

import com.majd.trainersmanagement.dto.TrainerProfileRequestDto;
import com.majd.trainersmanagement.dto.TrainerProfileResponseDto;

public interface ITrainerService {
    void createTrainerProfile(TrainerProfileRequestDto trainerProfileRequestDto, String email_account);

    TrainerProfileResponseDto getTrainerProfile(String emailAccount);
}
