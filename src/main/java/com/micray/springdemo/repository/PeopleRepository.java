package com.micray.springdemo.repository;

import com.micray.springdemo.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PeopleRepository extends CrudRepository<Person, UUID> {
}
