package com.example.simple.service;

import com.example.simple.domain.Simple;
import com.example.simple.util.FunctionalException;

import java.util.List;
import java.util.Optional;

public interface SimpleService {

    List<Simple> findAllSimple(Optional<String> name);

    Simple findSimpleById(String simpleId) throws FunctionalException;

    void saveSimple(String simpleId, Simple simple) throws FunctionalException;

    void deleteSimpleById(String simpleId) throws FunctionalException;
}
