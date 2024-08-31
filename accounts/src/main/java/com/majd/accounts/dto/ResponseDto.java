package com.majd.accounts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public class ResponseDto {
    private HttpStatus statusCode;

    private String statusMsg;

}