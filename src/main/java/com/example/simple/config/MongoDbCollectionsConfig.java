package com.example.simple.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.collections")
public class MongoDbCollectionsConfig {

    @NotNull
    private String simpleObjects;
}
