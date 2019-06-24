package com.example.simple.config;

import com.example.simple.config.exception.FunctionalException;
import com.example.simple.config.response.GlobalExceptionResponse;
import com.example.simple.config.util.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
                .code(ExceptionEnum.INTERNAL_SERVER_ERROR.getHttpStatus().value())
                .message(ExceptionEnum.INTERNAL_SERVER_ERROR.getMessage())
                .detail(ex.getClass().getSimpleName()
                        .concat(" : ")
                        .concat(ex.getMessage()))
                .build();
    }

    /**
     * Constraint violation exceptions
     *
     * @param ex ConstraintViolationException
     * @return GlobalExceptionResponse
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalExceptionResponse constraintExceptionHandler(Exception ex) {
        log.error("Exception thrown [{}]", ex.getClass().getSimpleName(), ex);

        return GlobalExceptionResponse.builder()
                .code(ExceptionEnum.INVALID_INPUT_PARAMETERS.getHttpStatus().value())
                .message(ExceptionEnum.INVALID_INPUT_PARAMETERS.getMessage())
                .detail(ex.getClass().getSimpleName()
                        .concat(" : ")
                        .concat(ex.getMessage()))
                .build();
    }

    @ExceptionHandler(FunctionalException.class)
    public GlobalExceptionResponse functionalExceptionHandler(HttpServletResponse response, FunctionalException ex) {
        log.error("FunctionalException thrown [{}]", ex.getExceptionEnum().getMessage(), ex);

        response.setStatus(ex.getExceptionEnum().getHttpStatus().value());

        return GlobalExceptionResponse.builder()
                .code(ex.getExceptionEnum().getHttpStatus().value())
                .message(ex.getExceptionEnum().getMessage())
                .detail(ex.getDetail())
                .build();
    }
}
