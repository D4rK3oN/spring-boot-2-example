package com.example.simple.config;

import com.example.simple.config.response.GlobalExceptionResponse;
import com.example.simple.config.util.ExceptionEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GlobalExceptionHandlerTest {

    private static final String PATH = "/test";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testExceptionHandler() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(PATH.concat("/exception"));

        final var response = testRestTemplate.getForEntity(builder.build().toUri(), GlobalExceptionResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(GlobalExceptionResponse.builder()
                        .code(ExceptionEnum.INTERNAL_SERVER_ERROR.getHttpStatus().value())
                        .message(ExceptionEnum.INTERNAL_SERVER_ERROR.getMessage())
                        .detail("Exception : Throw Exception")
                        .build(), response.getBody())
        );
    }

    @Test
    void testConstraintViolationExceptionHandler() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(PATH.concat("/constraintViolationException"));

        final var response = testRestTemplate.getForEntity(builder.build().toUri(), GlobalExceptionResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(GlobalExceptionResponse.builder()
                        .code(ExceptionEnum.INVALID_INPUT_PARAMETERS.getHttpStatus().value())
                        .message(ExceptionEnum.INVALID_INPUT_PARAMETERS.getMessage())
                        .detail("ConstraintViolationException : ")
                        .build(), response.getBody())
        );
    }

    @Test
    void testFunctionalExceptionHandler() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(PATH.concat("/functionalException"));

        final var response = testRestTemplate.getForEntity(builder.build().toUri(), GlobalExceptionResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(GlobalExceptionResponse.builder()
                        .code(ExceptionEnum.INVALID_INPUT_PARAMETERS.getHttpStatus().value())
                        .message(ExceptionEnum.INVALID_INPUT_PARAMETERS.getMessage())
                        .detail("Testing functional exception")
                        .build(), response.getBody())
        );
    }
}