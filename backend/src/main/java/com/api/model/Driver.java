package com.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("drivers")
public record Driver(
    @Id String id,
    String teamId,
    String name,
    String surname,
    String nationality,
    Integer number
){}
