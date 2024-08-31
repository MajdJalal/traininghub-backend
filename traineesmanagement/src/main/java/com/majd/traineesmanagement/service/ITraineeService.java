package com.majd.traineesmanagement.service;

import com.majd.traineesmanagement.dto.TraineeProfileRequestDto;
import com.majd.traineesmanagement.dto.TraineeProfileResponseDto;

public interface ITraineeService {
    void createTraineeProfile(TraineeProfileRequestDto traineeProfileRequestDto, String emailAccount);

    TraineeProfileResponseDto getTraineeProfile(String emailAccount);
}
