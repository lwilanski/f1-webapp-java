package com.api.model.dto;

public record QualyRowDto(
    String driverId,
    String driverName,
    String teamId,
    String teamName,
    String q1, String q2, String q3,
    Integer gridPosition
) {}
