package com.example.simple.web;

import com.example.simple.domain.Simple;
import com.example.simple.service.SimpleService;
import com.example.simple.util.FunctionalException;
import com.example.simple.web.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Optional;

@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated
public class SimpleController {

    private final SimpleService simpleService;

    @RequestMapping(method = {RequestMethod.GET})
    public SimpleResponse findAllSimpleResponse(
            @RequestParam(value = "name", required = false)
            @Size(min = 3, message = "The length of the name must be 3 or greater") final String name
    ) {
        return SimpleResponse.builder().simpleList(simpleService.findAllSimple(Optional.ofNullable(name))).build();
    }

    @RequestMapping(method = {RequestMethod.GET}, path = "/{simpleId}")
    public Simple findSimpleById(@PathVariable final String simpleId) throws FunctionalException {
        return simpleService.findSimpleById(simpleId);
    }

    @RequestMapping(method = {RequestMethod.PUT}, path = "/{simpleId}")
    public ResponseEntity saveSimple(@PathVariable @NotEmpty final String simpleId, @RequestBody Simple simple)
            throws FunctionalException {
        simpleService.saveSimple(simpleId, simple);

        return ResponseEntity.created(
                UriComponentsBuilder
                        .fromPath("/example/{simpleId}")
                        .buildAndExpand(simpleId)
                        .toUri()
        ).build();
    }
}
