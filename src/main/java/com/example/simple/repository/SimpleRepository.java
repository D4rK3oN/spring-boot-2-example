package com.example.simple.repository;

import com.example.simple.domain.Simple;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface SimpleRepository extends MongoRepository<Simple, String>, QuerydslPredicateExecutor<Simple> {

    Optional<Simple> findBySimpleId(String simpleId);

    @Query("{ 'age' : { $gte : ?0, $lte : ?1 } }")
    List<Simple> findAllByAgeBetween(int ageGTE, int ageLTE);

    @Query("{ 'name' : { $regex : ?0 , $options : 'i' } , 'age' : { $gte : ?1 , $lte : ?2 } }")
    List<Simple> findAllByCustomFilters(String name, int ageGTE, int ageLTE);

    List<Simple> findAllByNameIgnoreCaseLike(String name);
}
