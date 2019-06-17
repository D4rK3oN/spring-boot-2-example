package com.example.simple.repository;

import com.example.simple.config.MongoDbCollectionsConfig;
import com.example.simple.domain.Simple;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoOperations;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleRepositoryTest {

    private static final List<Simple> RESPONSE_OK = List.of(
            Simple.builder().id("5cd9768a7a7aea34787394d4").simpleId("00").name("Domino").build(),
            Simple.builder().id("5cd976ab7a7aea34787394d5").simpleId("01").name("Cable").build(),
            Simple.builder().id("5a993d5d9ccd732bf541a19f").simpleId("02").name("Psylocke").build(),
            Simple.builder().id("5cd976ab7acd732bf541a19f").simpleId("03").name("Colossus").build()
    );

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoDbCollectionsConfig mongoDbCollectionsConfig;

    @Autowired
    private SimpleRepository simpleRepository;

    @Test
    void findAllWhenExistData() throws IOException {
        final var mongodbFile = FileUtils.readFileToString(
                new ClassPathResource("mongodb/examples.simpleObjects.data.json").getFile(),
                Charset.defaultCharset()
        );

        final List<DBObject> dboList = (List<DBObject>) JSON.parse(mongodbFile);

        mongoOperations.dropCollection(mongoDbCollectionsConfig.getSimpleObjects());

        for (DBObject dbo : dboList)
            mongoOperations.save(dbo, mongoDbCollectionsConfig.getSimpleObjects());

        final var response = simpleRepository.findAll();

        assertAll(
                () -> assertFalse(response.isEmpty()),
                () -> assertEquals(4, response.size()),
                () -> assertEquals(RESPONSE_OK, response)
        );
    }

    @Test
    void findAllWhenNoExistData() throws IOException {
        final var mongodbFile = FileUtils.readFileToString(
                new ClassPathResource("mongodb/examples.simpleObjects.empty.json").getFile(),
                Charset.defaultCharset()
        );

        final List<DBObject> dboList = (List<DBObject>) JSON.parse(mongodbFile);

        mongoOperations.dropCollection(mongoDbCollectionsConfig.getSimpleObjects());

        for (DBObject dbo : dboList)
            mongoOperations.save(dbo, mongoDbCollectionsConfig.getSimpleObjects());

        final var response = simpleRepository.findAll();

        assertAll(
                () -> assertTrue(response != null && response.isEmpty()),
                () -> assertEquals(0, response.size()),
                () -> assertEquals(Collections.EMPTY_LIST, response)
        );
    }

    @Test
    void findAllWhenNoExistCollection() {
        mongoOperations.dropCollection(mongoDbCollectionsConfig.getSimpleObjects());

        final var response = simpleRepository.findAll();

        assertAll(
                () -> assertTrue(response != null && response.isEmpty()),
                () -> assertEquals(0, response.size()),
                () -> assertEquals(Collections.EMPTY_LIST, response)
        );
    }
}