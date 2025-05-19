package com.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("teams")
public record Team(
    @Id String id,
    String teamName,
    String teamNationality,
    String firstAppeareance,
    Integer constructorsChampionships,
    Integer driversChampionships
){}
