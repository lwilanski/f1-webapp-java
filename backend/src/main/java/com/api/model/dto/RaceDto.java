package com.api.model.dto;

import java.time.LocalDate;
import java.util.List;

public record RaceDto(
        LocalDate date,
        String raceName,
        String circuitName,
        String city,
        List<RaceRowDto> results
) {}
