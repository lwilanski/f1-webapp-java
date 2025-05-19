package com.api.model;

public record QualyRow(
    String classificationId,
    String driverId,
    String teamId,
    String q1,
    String q2,
    String q3,
    Integer gridPosition
){}
