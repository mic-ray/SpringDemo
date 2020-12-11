package com.micray.springdemo.api;

import com.micray.springdemo.model.Person;
import com.micray.springdemo.model.ResponseMessage;
import com.micray.springdemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person){
        return personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople(){
        return personService.getAllPeople();
    }

    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id){
        return personService.getPersonById(id).orElse(null);
    }

    @DeleteMapping(path="{id}")
    public UUID deletePersonById(@PathVariable("id") UUID id){
        return personService.deletePersonById(id);
    }

    @PutMapping(path="{id}")
    public ResponseEntity<ResponseMessage> updatePersonById(@PathVariable("id") UUID id, @RequestBody Person person){
        Person updatedPerson = personService.updatePersonById(id,person);
        return updatedPerson != null ? new ResponseEntity<>(
                new ResponseMessage("Updated resource!",updatedPerson),
                HttpStatus.OK

        ) : new ResponseEntity<>(
                new ResponseMessage("Resource was not updated!",null),
                HttpStatus.OK
        ) ;
    }
}
