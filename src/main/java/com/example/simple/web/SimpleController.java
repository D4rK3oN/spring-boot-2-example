package com.example.simple.web;

import com.example.simple.config.exception.FunctionalException;
import com.example.simple.domain.Simple;
import com.example.simple.service.SimpleService;
import com.example.simple.web.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SimpleController {

    private final SimpleService simpleService;

    @RequestMapping(method = {RequestMethod.GET})
    public SimpleResponse findAllSimpleResponse() {
        return SimpleResponse.builder().simpleList(simpleService.findAllSimple()).build();
    }

    @RequestMapping(method = {RequestMethod.GET}, path = "/{simpleId}")
    public Simple findSimpleById(@PathVariable final String simpleId) throws FunctionalException {
        return simpleService.findSimpleById(simpleId);
    }
}
