package com.example.simple.web;

import com.example.simple.config.exception.FunctionalException;
import com.example.simple.config.util.ExceptionEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public String testException() throws Exception {
        throw new Exception("Throw Exception");
    }

    @RequestMapping(value = "/constraintViolationException", method = RequestMethod.GET)
    public String testConstraintViolationException() throws ConstraintViolationException {
        throw new ConstraintViolationException(Collections.EMPTY_SET);
    }

    @RequestMapping(value = "/functionalException", method = RequestMethod.GET)
    public String testFunctionalException() throws FunctionalException {
        throw new FunctionalException("Throw FunctionalException",
                ExceptionEnum.INVALID_INPUT_PARAMETERS,
                "Testing functional exception");
    }
}
