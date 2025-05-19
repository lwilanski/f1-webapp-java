package com.api.model.dto;

public record RaceRowDto(
    Integer position,
    String driverId,
    String driverName,
    String teamId,
    String teamName,
    Integer grid,
    String time,
    Integer points,
    String fastLap
) {}
