package com.api.controller;

import com.api.model.Driver;
import com.api.repository.DriverRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/drivers")
class DriverController {
    private final DriverRepository repo;

    DriverController(DriverRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    List<Driver> all() {
        return repo.findAll();
    }
}
