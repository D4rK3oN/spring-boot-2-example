package com.example.simple.service;

import com.example.simple.domain.Simple;
import com.example.simple.repository.SimpleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleServiceImpl implements SimpleService {

    private final SimpleRepository simpleRepository;

    @Override
    public List<Simple> findAllSimple() {
        final var simpleList = simpleRepository.findAll();

        return simpleList != null ? simpleList : Collections.EMPTY_LIST;
    }
}
