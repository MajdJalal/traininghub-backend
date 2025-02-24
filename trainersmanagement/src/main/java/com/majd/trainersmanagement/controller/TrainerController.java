package com.majd.trainersmanagement.controller;


import com.majd.trainersmanagement.dto.ResponseDto;
import com.majd.trainersmanagement.dto.TrainerProfileRequestDto;
import com.majd.trainersmanagement.dto.TrainerProfileResponseDto;
import com.majd.trainersmanagement.service.ITrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/trainers-management/trainers-profiles")
public class TrainerController {

    private final ITrainerService iTrainerService;

    public TrainerController(ITrainerService iTrainerService) {
        this.iTrainerService = iTrainerService;
    }

    @Operation(
            summary = "create a new Trainer Profile for the current account",
            description = "accepts a dto and the email-account, does the validation, create the profile of the user of the basic values" +
                    "the api is called from a registered account so the registered email account is sent too"
    )
    @ApiResponse(
            responseCode = "201",
            description = "created successfully"
    )
    @PostMapping(value = "/{email-account}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> createTrainerProfile(@Valid @RequestBody TrainerProfileRequestDto trainerProfileRequestDto, @PathVariable("email-account") String email_account) {
        iTrainerService.createTrainerProfile(trainerProfileRequestDto, email_account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "created a new TrainerProfile"));

    }

    @Operation(
            summary = "get the trainer profile of the current account",
            description = "return an existing profile for the current account or null if there is no training profile associated with it"
    )
    @ApiResponses( {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully returned profile"
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully returned null, if there is no profile exists"
            )
        }
    )

    @GetMapping("{email-account}")
    public ResponseEntity<TrainerProfileResponseDto> getTrainerProfile(@PathVariable("email-account") String email_account) {
        TrainerProfileResponseDto trainerProfileResponseDto = iTrainerService.getTrainerProfile(email_account);
        return ResponseEntity.status(HttpStatus.OK)
                .body(trainerProfileResponseDto);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("")
    public ResponseEntity<List<TrainerProfileResponseDto>> getTrainerProfiles() {
        List<TrainerProfileResponseDto> trainerProfiles = iTrainerService.getTrainerProfiles();
        return ResponseEntity.status(HttpStatus.OK)
                .body(trainerProfiles);
    }


}
