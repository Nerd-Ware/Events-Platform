package com.example.eventplatform.repository;

import com.example.eventplatform.model.DbUser;
import org.springframework.data.repository.CrudRepository;

public interface DbUserRepository extends CrudRepository<DbUser,Integer> {
    public DbUser findByUsername(String username);
}
