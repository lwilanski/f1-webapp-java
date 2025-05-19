package com.api.model;

public record RaceRow(
    Integer position,
    String driverId,
    String teamId,
    Integer grid,
    String time,
    Integer points,
    String fastLap
) {}
