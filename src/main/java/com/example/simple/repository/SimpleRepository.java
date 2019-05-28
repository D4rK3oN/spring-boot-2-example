package com.example.simple.repository;

import com.example.simple.domain.Simple;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SimpleRepository extends MongoRepository<Simple, String> {
}
