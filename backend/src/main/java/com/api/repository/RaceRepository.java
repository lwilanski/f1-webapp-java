package com.api.repository;

import com.api.model.RaceDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RaceRepository extends MongoRepository<RaceDoc, String> {}
