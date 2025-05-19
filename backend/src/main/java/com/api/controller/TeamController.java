package com.api.controller;

import com.api.model.Team;
import com.api.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/teams")
class TeamController {
    private final TeamRepository repo;

    TeamController(TeamRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    List<Team> all() {
        return repo.findAll();
    }
}
