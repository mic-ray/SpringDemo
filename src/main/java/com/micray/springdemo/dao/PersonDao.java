package com.micray.springdemo.dao;

import com.micray.springdemo.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {
    int insertPerson(UUID id, Person person);
    default int insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id,person);
    }

    List<Person> selectAllPeople();

    Optional<Person> selectPersonById(String id);

    int deletePersonById(String id);

    int updatePersonById(String id, Person person);
}
