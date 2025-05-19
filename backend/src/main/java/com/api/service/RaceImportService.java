package com.api.service;

import com.api.model.*;
import com.api.repository.RaceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaceImportService {

    private static final String ENDPOINT = "https://f1api.dev/api/current/last/race";

    private final RestTemplate   restTemplate;
    private final RaceRepository repo;

    public RaceImportService(RestTemplate restTemplate, RaceRepository repo) {
        this.restTemplate = restTemplate;
        this.repo = repo;
    }

    public void importLatestRace() {
        JsonNode root = restTemplate.getForObject(ENDPOINT, JsonNode.class);
        if (root == null || root.path("races").isMissingNode()) return;

        JsonNode race = root.get("races");
        JsonNode rows = race.get("results");

        LocalDate date = LocalDate.parse(race.get("date").asText());
        String raceName = race.get("raceName").asText();
        String circuitName = race.get("circuit").get("circuitName").asText();
        String city = race.get("circuit").get("city").asText();

        List<RaceRow> results = new ArrayList<>();
        for (JsonNode n : rows) {
            results.add(new RaceRow(
                n.get("position").asInt(),
                n.get("driver").get("driverId").asText(),
                n.get("team").get("teamId").asText(),
                n.get("grid").asInt(),
                n.get("time").asText(),
                n.get("points").asInt(),
                n.path("fastLap").isNull() ? null : n.get("fastLap").asText()
            ));
        }

        RaceDoc doc = new RaceDoc("latest", date, raceName, circuitName, city, results);

        repo.deleteById("latest");
        repo.save(doc);
    }
}
