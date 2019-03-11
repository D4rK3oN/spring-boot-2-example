package com.example.simple.repository;

import com.example.simple.domain.Simple;

import java.util.List;

public interface SimpleRepository {

    List<Simple> findAll();
}
