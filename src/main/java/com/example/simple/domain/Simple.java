package com.example.simple.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@QueryEntity
@Document(collection = "#{@mongoDbCollectionsConfig.getSimpleObjects()}")
public class Simple {

    @Id
    @JsonIgnore
    private String id;

    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @JsonProperty("id")
    private String simpleId;

    private String name;

    @JsonIgnore
    public boolean isEmpty() {
        return (id == null || id.isEmpty())
                && (simpleId == null || simpleId.isEmpty())
                && (name == null || name.isEmpty());
    }
}
