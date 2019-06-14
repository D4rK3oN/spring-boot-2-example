package com.example.simple.config;

import com.example.simple.config.response.GlobalExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle default exceptions
     *
     * @param ex Exception
     * @return GlobalExceptionResponse
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GlobalExceptionResponse exceptionHandler(Exception ex) {
        log.error("Exception thrown [{}]", ex.getClass().getSimpleName(), ex);

        return GlobalExceptionResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal server error")
                .detail(ex.getClass().getSimpleName()
                        .concat(" : ")
                        .concat(ex.getMessage()))
                .build();
    }
}
