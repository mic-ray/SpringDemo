package com.micray.springdemo.service;

// import com.micray.springdemo.dao.PersonDao;
import com.micray.springdemo.model.Person;
import com.micray.springdemo.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    //private final PersonDao personDao;

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonService(//@Qualifier("fakeDao") PersonDao personDao,
                          PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
        //this.personDao = personDao;
    }

    public Person addPerson(Person person){
        return peopleRepository.save(person);
    }

    public List<Person> getAllPeople() {
        return (List<Person>) peopleRepository.findAll();
    }

    public Optional<Person> getPersonById(UUID id){
        return peopleRepository.findById(id);
    }

    public Boolean deletePersonById(UUID id){
        peopleRepository.deleteById(id);
        return true;
    }

    public Person updatePersonById(UUID id, Person person){
        Optional<Person> personOptional = peopleRepository.findById(id);
        if(personOptional.isPresent()){
            Person personToUpdate = personOptional.get();
            personToUpdate.setName(person.getName());
            return peopleRepository.save(personToUpdate);
        }
        return null;
    }
}
