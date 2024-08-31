package com.majd.trainersmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter

public class ResponseDto {
    private HttpStatus statusCode;

    private String statusMsg;


}