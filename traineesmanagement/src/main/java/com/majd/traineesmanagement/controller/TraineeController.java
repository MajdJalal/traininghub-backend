package com.majd.traineesmanagement.controller;


import com.majd.traineesmanagement.dto.ResponseDto;
import com.majd.traineesmanagement.dto.TraineeProfileRequestDto;
import com.majd.traineesmanagement.dto.TraineeProfileResponseDto;
import com.majd.traineesmanagement.service.ITraineeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trainees-management/trainee/v1")
public class TraineeController {

    private  final ITraineeService iTraineeService;

    public TraineeController(ITraineeService iTraineeService) {
        this.iTraineeService = iTraineeService;
    }

    @Operation(
            summary = "create a new Trainee Profile for the current account",
            description = "accepts a dto and the email-account, does the validation, create the profile of the user of the basic values" +
                    "the api is called from a registered account so the registered email account is sent too"
    )
    @ApiResponse(
            responseCode = "201",
            description = "created successfully"
    )
    @PostMapping("/trainees-profiles/{email-account}")
    public ResponseEntity<ResponseDto> createTraineeProfile(@Valid @RequestBody TraineeProfileRequestDto traineeProfileRequestDto, @PathVariable("email-account") String email_account) {
        iTraineeService.createTraineeProfile(traineeProfileRequestDto, email_account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "created a new TraineeProfile"));

    }

    @Operation(
            summary = "get the trainee profile of the current account",
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

    @GetMapping("/trainees-profiles/{email-account}")
    public ResponseEntity<TraineeProfileResponseDto> getTraineeProfile(@PathVariable("email-account") String email_account) {
        TraineeProfileResponseDto traineeProfileResponseDto = iTraineeService.getTraineeProfile(email_account);
        return ResponseEntity.status(HttpStatus.OK)
                .body(traineeProfileResponseDto);
    }
}
