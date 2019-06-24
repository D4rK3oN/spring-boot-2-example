package com.example.simple.service;

import com.example.simple.config.exception.FunctionalException;
import com.example.simple.domain.Simple;
import com.example.simple.web.response.SimpleResponse;

import java.util.List;
import java.util.Optional;

public interface SimpleService {

    List<Simple> findAllSimple(Optional<String> name);

    Simple findSimpleById(String simpleId) throws FunctionalException;
}
