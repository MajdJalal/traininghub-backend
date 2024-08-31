package com.majd.traineesmanagement.service.impl;

import com.majd.traineesmanagement.dto.TraineeProfileRequestDto;
import com.majd.traineesmanagement.dto.TraineeProfileResponseDto;
import com.majd.traineesmanagement.entity.TraineeProfile;
import com.majd.traineesmanagement.exception.AlreadyExistsException;
import com.majd.traineesmanagement.exception.NoProfileExistsException;
import com.majd.traineesmanagement.mapper.TraineeProfileMapper;
import com.majd.traineesmanagement.repository.TraineeRepository;
import com.majd.traineesmanagement.service.ITraineeService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TraineeService implements ITraineeService {

    private final TraineeRepository traineeRepository;
    private final TraineeProfileMapper traineeProfileMapper;

    public TraineeService(TraineeRepository traineeRepository, TraineeProfileMapper traineeProfileMapper) {
        this.traineeRepository = traineeRepository;
        this.traineeProfileMapper = traineeProfileMapper;
    }

    @Override
    public void createTraineeProfile(TraineeProfileRequestDto traineeProfileRequestDto, String emailAccount) {
        //TO_DO
        Optional<TraineeProfile> optionalTraineeProfile = traineeRepository.findByEmailAccount(emailAccount);
        if(optionalTraineeProfile.isPresent()) throw new AlreadyExistsException("trainee profile already exists for this account");
        TraineeProfile traineeProfile = traineeProfileMapper.toTraineeProfile(traineeProfileRequestDto, emailAccount);
        traineeRepository.save(traineeProfile);
        //add the email account too;
    }

    @Override
    public TraineeProfileResponseDto getTraineeProfile(String emailAccount) {
        //TO-DO
        Optional<TraineeProfile> optionalTraineeProfile = traineeRepository.findByEmailAccount(emailAccount);
        if(optionalTraineeProfile.isEmpty()) throw new NoProfileExistsException("no trainee profile exists for this account");
        TraineeProfile traineeProfile = optionalTraineeProfile.get();
        TraineeProfileResponseDto traineeProfileResponseDto = traineeProfileMapper.toTraineeProfileResponseDto(traineeProfile);
        return traineeProfileResponseDto;
    }
}
