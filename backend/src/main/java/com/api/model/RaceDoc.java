package com.api.model;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("latest_race")
public record RaceDoc(
    @Id String id,
    LocalDate date,
    String raceName,
    String circuitName,
    String city,
    List<RaceRow> results
) {}
