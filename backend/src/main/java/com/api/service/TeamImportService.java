package com.api.service;

import com.api.model.Team;
import com.api.repository.TeamRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamImportService {

    private final RestTemplate restTemplate;
    private final TeamRepository repo;

    public TeamImportService(RestTemplate restTemplate, TeamRepository repo) {
        this.restTemplate = restTemplate;
        this.repo = repo;
    }

    public void importTeams() {

        JsonNode root = restTemplate.getForObject(
                "https://f1api.dev/api/current/teams", JsonNode.class);

        if (root == null || !root.has("teams")) return;

        List<Team> list = new ArrayList<>();
        for (JsonNode n : root.get("teams")) {
            list.add(new Team(
                    n.get("teamId").asText(),
                    n.get("teamName").asText(),
                    n.get("teamNationality").asText(),
                    n.get("firstAppeareance").asText(),
                    n.get("constructorsChampionships").asInt(),
                    n.get("driversChampionships").asInt()
            ));
        }

        repo.deleteAll();
        repo.saveAll(list);
    }
}
