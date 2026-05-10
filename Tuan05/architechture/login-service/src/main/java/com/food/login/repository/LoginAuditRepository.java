package com.food.login.repository;

import com.food.login.model.LoginAudit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginAuditRepository extends MongoRepository<LoginAudit, String> {
}
