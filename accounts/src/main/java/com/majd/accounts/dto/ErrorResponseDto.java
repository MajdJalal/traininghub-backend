package com.majd.accounts.dto;

import lombok.Getter;

import java.time.LocalDate;


@Getter
public class ErrorResponseDto {

    private String errorCode;

    private String errorMessage;

    private LocalDate errorTime;

    ErrorResponseDto(String errorCode, String errorMessage, LocalDate errorTime) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorTime = errorTime;
    }

    public static ErrorResponseDtoBuilder builder() {
        return new ErrorResponseDtoBuilder();
    }

    public static class ErrorResponseDtoBuilder {
        private String errorCode;
        private String errorMessage;
        private LocalDate errorTime;

        ErrorResponseDtoBuilder() {
        }

        public ErrorResponseDtoBuilder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorResponseDtoBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ErrorResponseDtoBuilder errorTime(LocalDate errorTime) {
            this.errorTime = errorTime;
            return this;
        }

        public ErrorResponseDto build() {
            return new ErrorResponseDto(this.errorCode, this.errorMessage, this.errorTime);
        }

        public String toString() {
            return "ErrorResponseDto.ErrorResponseDtoBuilder(errorCode=" + this.errorCode + ", errorMessage=" + this.errorMessage + ", errorTime=" + this.errorTime + ")";
        }
    }
}
