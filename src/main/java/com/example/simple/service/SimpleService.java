package com.example.simple.service;

import com.example.simple.util.FunctionalException;
import com.example.simple.domain.Simple;

import java.util.List;
import java.util.Optional;

public interface SimpleService {

    List<Simple> findAllSimple(Optional<String> name);

    Simple findSimpleById(String simpleId) throws FunctionalException;
}
