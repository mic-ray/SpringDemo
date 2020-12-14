package com.micray.springdemo.api;

import com.micray.springdemo.exception.ApiRequestException;
import com.micray.springdemo.model.Person;
import com.micray.springdemo.model.ResponseMessage;
import com.micray.springdemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        List<Person> people = personService.getAllPeople();
        if(people == null){
            throw new ApiRequestException("Resources not found", HttpStatus.NOT_FOUND);
        }
        return people;
    }

    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id){
        Optional<Person> personOptional = personService.getPersonById(id);
        if(!personOptional.isPresent()){
            throw new ApiRequestException("Resource not found",HttpStatus.NOT_FOUND);
        }
        return personOptional.get();
    }

    @DeleteMapping(path="{id}")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable("id") UUID id){
        Boolean deleted = personService.deletePersonById(id);
        if(!deleted){
            throw new ApiRequestException("Resource was not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="{id}")
    public ResponseEntity<ResponseMessage> updatePersonById(@PathVariable("id") UUID id, @RequestBody Person person){
        Person updatedPerson = personService.updatePersonById(id,person);

        if(updatedPerson == null) {
            throw new ApiRequestException("Resource was not updated", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                new ResponseMessage("Updated resource",updatedPerson),
                HttpStatus.OK
        );
    }
}
