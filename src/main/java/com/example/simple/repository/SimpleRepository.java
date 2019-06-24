package com.example.simple.repository;

import com.example.simple.domain.Simple;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SimpleRepository extends MongoRepository<Simple, String> {

    Optional<Simple> findBySimpleId(String simpleId);

    List<Simple> findAllByNameIgnoreCaseLike(String name);
}
