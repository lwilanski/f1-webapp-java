package com.api.bootstrap;

import com.api.service.DriverImportService;
import com.api.service.TeamImportService;
import com.api.service.QualyImportService;
import com.api.service.RaceImportService;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBootstrap implements ApplicationRunner {

    private final DriverImportService driverService;
    private final TeamImportService teamService;
    private final QualyImportService qualyService;
    private final RaceImportService raceService;

    public DataBootstrap(DriverImportService driverService, TeamImportService teamService, 
            QualyImportService qualyService, RaceImportService raceService) {
        this.driverService = driverService;
        this.teamService = teamService;
        this.qualyService = qualyService;
        this.raceService = raceService;
    }

    @Override
    public void run(ApplicationArguments args) {
        driverService.importDrivers();
        teamService.importTeams();
        qualyService.importLatestQualy();
        raceService.importLatestRace();
    }
}
