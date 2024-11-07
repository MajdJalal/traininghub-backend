package com.majd.trainersmanagement.service.impl;


import com.majd.trainersmanagement.dto.TrainerProfileRequestDto;
import com.majd.trainersmanagement.dto.TrainerProfileResponseDto;
import com.majd.trainersmanagement.entity.TrainerProfile;
import com.majd.trainersmanagement.exception.AlreadyExistsException;
import com.majd.trainersmanagement.exception.NoProfileExistsException;
import com.majd.trainersmanagement.mapper.TrainerProfileMapper;
import com.majd.trainersmanagement.repository.TrainerProfileRepository;
import com.majd.trainersmanagement.service.ITrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class TrainerService implements ITrainerService {

    private final Logger logger = LoggerFactory.getLogger(TrainerService.class);
    private final TrainerProfileMapper trainerProfileMapper;
    private final TrainerProfileRepository trainerProfileRepository;

    public TrainerService(TrainerProfileMapper trainerProfileMapper, TrainerProfileRepository trainerProfileRepository) {
        this.trainerProfileMapper = trainerProfileMapper;
        this.trainerProfileRepository = trainerProfileRepository;
    }

    @Override
    public void createTrainerProfile(TrainerProfileRequestDto trainerProfileRequestDto, String emailAccount) {
        Optional<TrainerProfile> optionalTrainerProfile = trainerProfileRepository.findByEmailAccount(emailAccount);
        if(optionalTrainerProfile.isPresent()) throw new AlreadyExistsException("trainer profile already exists for this account");
        //dont forget to insert the email-account in the trainer-profile(did that in the mapper)
        TrainerProfile trainerProfile = trainerProfileMapper.toTrainerProfile(trainerProfileRequestDto, emailAccount);
        trainerProfileRepository.save(trainerProfile);
    }

    @Override
    public TrainerProfileResponseDto getTrainerProfile(String emailAccount) {
        Optional<TrainerProfile> optionalTrainerProfile = trainerProfileRepository.findByEmailAccount(emailAccount);
        if(optionalTrainerProfile.isEmpty()) throw new NoProfileExistsException("there is no trainer profile for this account");
        TrainerProfile trainerProfile = optionalTrainerProfile.get();
        TrainerProfileResponseDto trainerProfileResponseDto = trainerProfileMapper.toTrainerProfileResponseDto(trainerProfile);
        return trainerProfileResponseDto;
    }

    @Override
    public List<TrainerProfileResponseDto> getTrainerProfiles() {
        List<TrainerProfile> profiles =  trainerProfileRepository.findAll();
        List<TrainerProfileResponseDto> respnse = profiles.stream()
                .map(prof -> trainerProfileMapper.toTrainerProfileResponseDto(prof))
                .collect(Collectors.toList());
        return respnse;
    }


}
