package com.example.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.d4rk3on", "com.example.simple"})
@EnableMongoRepositories
public class SimpleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }
}
