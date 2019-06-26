package com.example.simple.service;

import com.example.simple.util.FunctionalException;
import com.example.simple.domain.Simple;
import com.example.simple.repository.SimpleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleServiceTest {

    private static final List<Simple> SIMPLE_LIST_OK = List.of(
            Simple.builder().id("id_001").simpleId("01").name("Domino").build(),
            Simple.builder().id("id_002").simpleId("02").name("Cable").build(),
            Simple.builder().id("id_003").simpleId("03").name("Psylocke").build(),
            Simple.builder().id("id_004").simpleId("04").name("Colossus").build()
    );

    @Mock
    private SimpleRepository simpleRepository;

    @InjectMocks
    private SimpleServiceImpl simpleService;

    @Test
    void findAllSimpleWhenOk() {
        when(simpleRepository.findAll()).thenReturn(SIMPLE_LIST_OK);

        final var response = simpleService.findAllSimple(Optional.empty());

        assertAll(
                () -> assertTrue(response != null && !response.isEmpty()),
                () -> assertEquals(4, response.size()),
                () -> assertEquals(SIMPLE_LIST_OK, response)
        );
    }

    @Test
    void findAllSimpleWhenNoDataFound() {
        when(simpleRepository.findAll()).thenReturn(List.of());

        final var emptyResponse = simpleService.findAllSimple(Optional.empty());

        assertAll(
                () -> assertTrue(emptyResponse != null && emptyResponse.isEmpty()),
                () -> assertEquals(0, emptyResponse.size())
        );

        when(simpleRepository.findAll()).thenReturn(null);

        final var nullResponse = simpleService.findAllSimple(Optional.empty());

        assertAll(
                () -> assertTrue(nullResponse != null && nullResponse.isEmpty()),
                () -> assertEquals(0, nullResponse.size())
        );
    }

    @Test
    void findAllSimpleByNameWhenOk() {
        when(simpleRepository.findAllByNameIgnoreCaseLike(SIMPLE_LIST_OK.get(0).getName()))
                .thenReturn(List.of(SIMPLE_LIST_OK.get(0)));

        final var response = simpleService.findAllSimple(Optional.of(SIMPLE_LIST_OK.get(0).getName()));

        assertAll(
                () -> assertTrue(response != null && !response.isEmpty()),
                () -> assertEquals(1, response.size()),
                () -> assertEquals(List.of(SIMPLE_LIST_OK.get(0)), response)
        );
    }

    @Test
    void findSimpleByIdWhenExistData() {
        when(simpleRepository.findBySimpleId("01"))
                .thenReturn(Optional.of(Simple.builder()
                        .id("id_001")
                        .simpleId("01")
                        .name("Domino")
                        .build()));

        final var response = simpleService.findSimpleById("01");

        assertAll(
                () -> assertNotNull(response),
                () -> assertFalse(response.isEmpty()),
                () -> assertEquals(Simple.builder().id("id_001").simpleId("01").name("Domino").build(), response)
        );
    }

    @Test
    void findSimpleByIdWhenNoDataFound() {
        when(simpleRepository.findBySimpleId("00")).thenReturn(Optional.empty());

        assertThrows(FunctionalException.class, () -> simpleService.findSimpleById("00"));
    }
}