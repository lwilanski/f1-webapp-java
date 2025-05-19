package com.api.service;

import com.api.model.Driver;
import com.api.repository.DriverRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverImportService {

    private final RestTemplate restTemplate;
    private final DriverRepository repo;

    public DriverImportService(RestTemplate restTemplate, DriverRepository repo) {
        this.restTemplate = restTemplate;
        this.repo = repo;
    }

    public void importDrivers() {

        JsonNode root = restTemplate.getForObject(
                "https://f1api.dev/api/current/drivers", JsonNode.class);

        if (root == null || !root.has("drivers")) return;

        List<Driver> list = new ArrayList<>();
        for (JsonNode n : root.get("drivers")) {
            list.add(new Driver(
                    n.get("driverId").asText(),
                    n.get("teamId").asText(),
                    n.get("name").asText(),
                    n.get("surname").asText(),
                    n.get("nationality").asText(),
                    n.get("number").asInt()
            ));
        }

        repo.deleteAll();
        repo.saveAll(list);
    }
}
