package com.example.simple.repository;

import com.example.simple.domain.Simple;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimpleRepositoryImpl implements SimpleRepository {

    private final List<Simple> mockList = List.of(
            Simple.builder().id("00").name("Simple Name 00").build(),
            Simple.builder().id("01").name("Simple Name 01").build(),
            Simple.builder().id("02").name("Simple Name 02").build(),
            Simple.builder().id("03").name("Simple Name 03").build()
    );

    @Override
    public List<Simple> findAll() {
        return mockList;
    }
}
