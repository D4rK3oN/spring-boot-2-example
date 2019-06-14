package com.example.simple.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/commonException", method = RequestMethod.GET)
    public String testingException() throws Exception {
        throw new Exception("Testing common exception");
    }
}
