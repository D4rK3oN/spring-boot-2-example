package com.example.simple.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Simple {

    private String id;
    private String name;
}
