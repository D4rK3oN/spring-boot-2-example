package com.example.simple.web.response;

import com.example.simple.domain.Simple;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SimpleResponse {

    @JsonProperty("list")
    private List<Simple> simpleList;
}
