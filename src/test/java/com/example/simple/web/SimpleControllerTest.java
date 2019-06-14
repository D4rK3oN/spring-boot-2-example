package com.example.simple.web;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleControllerTest {

    private static final String PATH = "/example";
    private static final List<Simple> RESPONSE_OK = List.of(
            Simple.builder().documentId("id_001").id("01").name("Domino").build(),
            Simple.builder().documentId("id_002").id("02").name("Cable").build(),
            Simple.builder().documentId("id_003").id("03").name("Psylocke").build(),
            Simple.builder().documentId("id_004").id("04").name("Colossus").build()
    );

    @MockBean
    private SimpleService simpleService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void findAllSimpleResponseWhenOk() {
        when(simpleService.findAllSimple()).thenReturn(RESPONSE_OK);

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(PATH);

        var response = testRestTemplate.getForEntity(builder.build().toUri(), SimpleResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertTrue(response.getBody() != null && !response.getBody().getSimpleList().isEmpty()),
                () -> assertEquals(4, response.getBody().getSimpleList().size()),
                () -> assertNotEquals(SimpleResponse.builder().simpleList(RESPONSE_OK).build(), response.getBody()),
                () -> assertEquals(SimpleResponse.builder().simpleList(List.of(
                        Simple.builder().id("01").name("Domino").build(),
                        Simple.builder().id("02").name("Cable").build(),
                        Simple.builder().id("03").name("Psylocke").build(),
                        Simple.builder().id("04").name("Colossus").build()
                )).build(), response.getBody())
        );
    }

    @Test
    void findAllSimpleResponseWhenNoDataFound() {
        when(simpleService.findAllSimple()).thenReturn(Collections.EMPTY_LIST);

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(PATH);

        final var response = testRestTemplate.getForEntity(builder.build().toUri(), SimpleResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertTrue(response.getBody() != null && response.getBody().getSimpleList().isEmpty()),
                () -> assertEquals(0, response.getBody().getSimpleList().size()),
                () -> assertEquals(SimpleResponse.builder().simpleList(Collections.EMPTY_LIST).build(), response.getBody())
        );
    }
}