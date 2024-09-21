package com.majd.accounts.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
@Builder
public class ResponseDto<T> {
    private T data;
    private HttpStatus statusCode;

    private String statusMsg;

}