package com.api.service;

import com.api.model.QualyDoc;
import com.api.model.QualyRow;
import com.api.repository.QualyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class QualyImportService {

    private static final String ENDPOINT = "https://f1api.dev/api/current/last/qualy";

    private final RestTemplate restTemplate;
    private final QualyRepository repo;

    public QualyImportService(RestTemplate restTemplate, QualyRepository repo) {
        this.restTemplate = restTemplate;
        this.repo = repo;
    }

    public void importLatestQualy() {
        JsonNode root = restTemplate.getForObject(ENDPOINT, JsonNode.class);
        if (root == null || !root.has("races")) return;

        JsonNode race = root.get("races");
        JsonNode rows = race.get("qualyResults");

        String rawDate = race.path("qualyTime").asText(null);
        String rawTime = race.path("qualyDate").asText(null);

        if (rawDate != null && rawDate.contains(":")) {
            rawDate = rawTime;
        }

        LocalDate date = LocalDate.parse(rawDate);

        String raceName = race.get("raceName").asText();
        String circuitName = race.get("circuit").get("circuitName").asText();
        String city = race.get("circuit").get("city").asText();

        List<QualyRow> results = new ArrayList<>();
        for (JsonNode n : rows) {
            results.add(new QualyRow(
                n.get("classificationId").asText(),
                n.get("driverId").asText(),
                n.get("teamId").asText(),
                n.path("q1").asText(null),
                n.path("q2").asText(null),
                n.path("q3").asText(null),
                n.get("gridPosition").asInt()
            ));
        }

        QualyDoc doc = new QualyDoc(
            "latest",
            date,
            raceName,
            circuitName,
            city,
            results
        );

        repo.deleteById("latest");
        repo.save(doc);
    }
}
