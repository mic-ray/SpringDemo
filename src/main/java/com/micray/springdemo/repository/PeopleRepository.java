package com.micray.springdemo.repository;

import com.micray.springdemo.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PeopleRepository extends MongoRepository<Person, String> {
    Optional<Person> findByName(String name);
}
