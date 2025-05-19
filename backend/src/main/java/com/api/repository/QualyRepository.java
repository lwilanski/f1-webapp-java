package com.api.repository;

import com.api.model.QualyDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QualyRepository extends MongoRepository<QualyDoc,String> {}

