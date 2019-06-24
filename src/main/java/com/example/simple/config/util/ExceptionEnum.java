package com.example.simple.config.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionEnum {

    INVALID_INPUT_PARAMETERS(HttpStatus.BAD_REQUEST, "Invalid input parameters"),
    NO_DATA_FOUND(HttpStatus.NOT_FOUND, "No data found"),
    NO_CONTENT(HttpStatus.NO_CONTENT, "No content"),
    NO_AUTH_ACCESS(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    NO_AUTH_INFO(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Technical error in the system");

    @Getter
    private HttpStatus httpStatus;

    @Getter
    private String message;

    ExceptionEnum(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
