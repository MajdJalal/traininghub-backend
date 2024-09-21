package com.majd.accounts.controller;


import com.majd.accounts.dto.AccountRequestDto;
import com.majd.accounts.dto.ErrorResponseDto;
import com.majd.accounts.dto.ResponseDto;
import com.majd.accounts.model.AccountModel;
import com.majd.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/v1")
//@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    private final IAccountService iAccountService;
    private final RedisTemplate<String, String> redisTemplate;

    public AccountController(IAccountService iAccountService, RedisTemplate<String, String> redisTemplate) {
        this.iAccountService = iAccountService;
        this.redisTemplate = redisTemplate;
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
                .body(ResponseDto.builder().statusMsg("created a new account").build());
    }

    @Operation(
            summary = "get all accounts",
            description = "based on client credentials grant we get an access token to be able to retrieve all users(accounts) in a specific realm(trainingHubV1)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "20",
                    description = "OK"
            )
    }
    )
    @GetMapping
    public ResponseEntity<ResponseDto<List<AccountModel>>> getAccounts() {
        List<AccountModel> accountModels = iAccountService.getAccounts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<List<AccountModel>>builder()
                                .data(accountModels).build());
    }

    /**
     * Only to test redis is working, now actual required functionality
     */
//    @PostMapping("/redis")
//    public ResponseEntity<ResponseDto> storeRedisTest() {
//        redisTemplate.opsForValue().set("Name", "Majd");
//        String value = redisTemplate.opsForValue().get("Name");
//        System.out.println("Stored value: " + value);  // Should print "Majd"
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new ResponseDto(HttpStatus.CREATED, value));
//    }


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
