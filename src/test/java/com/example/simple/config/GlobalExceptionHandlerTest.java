package com.example.simple.config;

import com.example.simple.config.response.GlobalExceptionResponse;
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
    void exceptionHandler() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(PATH.concat("/commonException"));

        final var response = testRestTemplate.getForEntity(builder.build().toUri(), GlobalExceptionResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(GlobalExceptionResponse.builder()
                        .code(500)
                        .message("Internal server error")
                        .detail("Exception : Testing common exception")
                        .build(), response.getBody())
        );
    }
}