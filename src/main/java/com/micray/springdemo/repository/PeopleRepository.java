package com.micray.springdemo.repository;

import com.micray.springdemo.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PeopleRepository extends CrudRepository<Person, UUID> {
}
