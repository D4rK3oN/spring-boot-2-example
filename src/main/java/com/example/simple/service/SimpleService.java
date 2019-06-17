package com.example.simple.service;

import com.example.simple.domain.Simple;

import java.util.List;

public interface SimpleService {

    List<Simple> findAllSimple();

    Simple findSimpleById(String simpleId) throws Exception;
}
