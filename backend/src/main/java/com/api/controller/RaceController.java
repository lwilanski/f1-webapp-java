package com.api.controller;

import com.api.model.dto.*;
import com.api.model.*;
import com.api.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/race")
public class RaceController {

    private final RestTemplate restTemplate;
    private final RaceRepository raceRepo;
    private final DriverRepository driverRepo;
    private final TeamRepository teamRepo;

    public RaceController(RestTemplate restTemplate, RaceRepository raceRepo, DriverRepository driverRepo, TeamRepository teamRepo) {
        this.restTemplate = restTemplate;
        this.raceRepo = raceRepo;
        this.driverRepo = driverRepo;
        this.teamRepo = teamRepo;
    }

    @GetMapping("/latest")
    public RaceDto latest() {
        RaceDoc doc = raceRepo.findById("latest").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<String> driverIds = doc.results().stream()
                                    .map(RaceRow::driverId).toList();
        List<String> teamIds = doc.results().stream()
                                    .map(RaceRow::teamId).toList();

        Map<String, Driver> drivers = StreamSupport
                .stream(driverRepo.findAllById(driverIds).spliterator(), false)
                .collect(Collectors.toMap(Driver::id, d -> d));

        Map<String, Team> teams = StreamSupport
                .stream(teamRepo.findAllById(teamIds).spliterator(), false)
                .collect(Collectors.toMap(Team::id, t -> t));

        List<RaceRowDto> rows = doc.results().stream()
                .map(r -> {
                    Driver d = drivers.get(r.driverId());
                    Team   t = teams.get(r.teamId());
                    return new RaceRowDto(
                            r.position(),
                            r.driverId(),
                            d.name() + " " + d.surname(),
                            r.teamId(),
                            t.teamName(),
                            r.grid(),
                            r.time(),
                            r.points(),
                            r.fastLap()
                    );
                })
                .toList();

        return new RaceDto(doc.date(), doc.raceName(), doc.circuitName(), doc.city(), rows);
    }

    @GetMapping("/{year}/{round}")
    public ResponseEntity<String> raceByYearRound(@PathVariable int year,
                                                @PathVariable int round) {
        String url = "https://f1api.dev/api/%d/%d/race".formatted(year, round);

        try {
            String json = restTemplate.getForObject(url, String.class);
            if (json == null || json.isBlank())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(json);

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RestClientException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY, "Upstream API error", e);
        }
    }
}
