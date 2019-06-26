package com.example.simple.web;

import com.example.simple.util.FunctionalException;
import com.example.simple.web.response.GlobalExceptionResponse;
import com.example.simple.util.ExceptionEnum;
import com.example.simple.domain.Simple;
import com.example.simple.service.SimpleService;
import com.example.simple.web.response.SimpleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleControllerTest {

    private static final String PATH = "/example";
    private static final List<Simple> SIMPLE_LIST_OK = List.of(
            Simple.builder().id("id_001").simpleId("01").name("Domino").build(),
            Simple.builder().id("id_002").simpleId("02").name("Cable").build(),
            Simple.builder().id("id_003").simpleId("03").name("Psylocke").build(),
            Simple.builder().id("id_004").simpleId("04").name("Colossus").build()
    );

    @MockBean
    private SimpleService simpleService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void findAllSimpleResponseWhenOk() {
        when(simpleService.findAllSimple(Optional.empty())).thenReturn(SIMPLE_LIST_OK);

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(PATH);

        var response = testRestTemplate.getForEntity(builder.build().toUri(), SimpleResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertTrue(response.getBody() != null && !response.getBody().getSimpleList().isEmpty()),
                () -> assertEquals(4, response.getBody().getSimpleList().size()),
                () -> assertNotEquals(SimpleResponse.builder().simpleList(SIMPLE_LIST_OK).build(), response.getBody()),
                () -> assertEquals(SimpleResponse.builder().simpleList(List.of(
                        Simple.builder().simpleId("01").name("Domino").build(),
                        Simple.builder().simpleId("02").name("Cable").build(),
                        Simple.builder().simpleId("03").name("Psylocke").build(),
                        Simple.builder().simpleId("04").name("Colossus").build()
                )).build(), response.getBody())
        );
    }

    @Test
    void findAllSimpleResponseWhenNoDataFound() {
        when(simpleService.findAllSimple(Optional.empty())).thenReturn(Collections.EMPTY_LIST);

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(PATH);

        final var response = testRestTemplate.getForEntity(builder.build().toUri(), SimpleResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertTrue(response.getBody() != null && response.getBody().getSimpleList().isEmpty()),
                () -> assertEquals(0, response.getBody().getSimpleList().size()),
                () -> assertEquals(SimpleResponse.builder().simpleList(Collections.EMPTY_LIST).build(), response.getBody())
        );
    }

    @Test
    void findSimpleByIdWhenOk() {
        when(simpleService.findSimpleById("01"))
                .thenReturn(Simple.builder()
                        .id("id_001")
                        .simpleId("01")
                        .name("Domino")
                        .build());

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(PATH.concat("/01"));

        var response = testRestTemplate.getForEntity(builder.build().toUri(), Simple.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(Simple.builder().simpleId("01").name("Domino").build(), response.getBody())
        );
    }

    @Test
    void findSimpleByIdWhenNoDataFound() {
        when(simpleService.findSimpleById("00"))
                .thenThrow(new FunctionalException(
                        "Not valid findBySimpleId response",
                        ExceptionEnum.NO_DATA_FOUND,
                        "ID [00] not exist"));

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(PATH.concat("/00"));

        final var response = testRestTemplate.getForEntity(builder.build().toUri(), GlobalExceptionResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(GlobalExceptionResponse.builder()
                        .code(ExceptionEnum.NO_DATA_FOUND.getHttpStatus().value())
                        .message(ExceptionEnum.NO_DATA_FOUND.getMessage())
                        .detail("ID [00] not exist")
                        .build(), response.getBody())
        );
    }
}