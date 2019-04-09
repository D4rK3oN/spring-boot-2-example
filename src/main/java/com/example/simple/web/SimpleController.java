package com.example.simple.web;

import com.example.simple.service.SimpleService;
import com.example.simple.web.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SimpleController {

    private final SimpleService simpleService;

    @RequestMapping(method = {RequestMethod.GET})
    public SimpleResponse findAll() {
        final var list = simpleService.findAllSimple();

        return SimpleResponse.builder().simpleList(list).build();
    }
}
