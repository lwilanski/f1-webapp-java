package com.api.controller;

import com.api.model.*;
import com.api.model.dto.*;
import com.api.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/qualy")
public class QualyController {

    private final QualyRepository  qualyRepo;
    private final DriverRepository driverRepo;
    private final TeamRepository   teamRepo;

    public QualyController(QualyRepository qualyRepo, DriverRepository driverRepo, TeamRepository teamRepo) {
        this.qualyRepo  = qualyRepo;
        this.driverRepo = driverRepo;
        this.teamRepo   = teamRepo;
    }

    @GetMapping("/latest")
    public QualyDto latest() {

        QualyDoc doc = qualyRepo.findById("latest").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<String> driverIds = doc.results().stream()
                                    .map(QualyRow::driverId).toList();
        List<String> teamIds   = doc.results().stream()
                                    .map(QualyRow::teamId).toList();

        Map<String, Driver> drivers = StreamSupport
                .stream(driverRepo.findAllById(driverIds).spliterator(), false)
                .collect(Collectors.toMap(Driver::id, d -> d));

        Map<String, Team> teams = StreamSupport
                .stream(teamRepo.findAllById(teamIds).spliterator(), false)
                .collect(Collectors.toMap(Team::id, t -> t));

        List<QualyRowDto> rows = doc.results().stream()
                .map(r -> {
                    Driver d = drivers.get(r.driverId());
                    Team   t = teams.get(r.teamId());
        
                    return new QualyRowDto(
                            r.driverId(),
                            d.name() + " " + d.surname(),
                            r.teamId(),
                            t.teamName(),
                            r.q1(), r.q2(), r.q3(),
                            r.gridPosition()
                    );
                })
                .toList();
        

        return new QualyDto(doc.date(), doc.raceName(),doc.circuitName(), doc.city(), rows);
    }
}
