package com.micray.springdemo.dao;

import com.micray.springdemo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        //DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(String id) {
        return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public int deletePersonById(String id) {
        Optional<Person> personOptional = selectPersonById(id);
        if (!personOptional.isPresent()) {
            return 0;
        }
        DB.remove(personOptional.get());
        return 1;
    }

    @Override
    public int updatePersonById(String id, Person person) {
        return selectPersonById(id).map(p -> {
            int updateIndex = DB.indexOf(p);
            if (updateIndex >= 0) {
                DB.set(updateIndex, person); //new Person(id, person.getName()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }
}
