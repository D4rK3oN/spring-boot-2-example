package com.example.simple.config.exception;

import com.example.simple.config.util.ExceptionEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class FunctionalException extends RuntimeException {

    @Getter
    private ExceptionEnum exceptionEnum;

    @Getter
    private String detail;

    public FunctionalException(String message, ExceptionEnum exceptionEnum) {
        super(message);
        this.exceptionEnum = exceptionEnum;
    }

    public FunctionalException(String message, ExceptionEnum exceptionEnum, String detail) {
        super(message);
        this.exceptionEnum = exceptionEnum;
        this.detail = detail;
    }
}
