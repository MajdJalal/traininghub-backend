package com.majd.accounts.controller;


import com.majd.accounts.dto.AccountRequestDto;
import com.majd.accounts.dto.ErrorResponseDto;
import com.majd.accounts.dto.ResponseDto;
import com.majd.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/v1")
//@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    private final IAccountService iAccountService;

    public AccountController(IAccountService iAccountService) {
        this.iAccountService = iAccountService;
    }

    @Operation(
            summary = "create a account",
            description = "accepts a dto, to register a new account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Account CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody AccountRequestDto accountRequestDto) {
        iAccountService.createAccount(accountRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "created a new account"));
    }
    @GetMapping
    public ResponseEntity<ResponseDto> getAccounts() {
        //TODO
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "account list"));
    }


//    @Operation(
//            summary = "log in with an existing account",
//            description = "accepts a dto, to check if the account exists"
//    )
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "successfully logged in"
//            ),
//            @ApiResponse(
//                    responseCode = "500",
//                    description = "HTTP Status Internal Server Error",
//                    content = @Content(
//                            schema = @Schema(implementation = ErrorResponseDto.class)
//                    )
//            )
//    }
//    )
//    @GetMapping
//    public ResponseEntity<ResponseDto> login(@Valid @RequestBody AccountRequestDto accountRequestDto) {
//        String token = iAccountService.login(accountRequestDto);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseDto(HttpStatus.OK, token));
//    }

}
