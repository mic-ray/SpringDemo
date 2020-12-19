package com.micray.springdemo.api;

import com.micray.springdemo.model.Person;
import com.micray.springdemo.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void getPersonByNameShouldReturnPerson() throws Exception {
        when(personService.getPersonByName("Mike")).thenReturn(new Person("Mike"));

        this.mockMvc.perform(get("/api/v1/person/name?name=Mike")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mike"));

        // If a name is passed, that is not provided by the PersonService it should return a 404
        this.mockMvc.perform(get("/api/v1/person/name?name=Mickey")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource not found"));
    }

    @Test
    void getPersonByNameShouldReturnNotFound() throws Exception {
        when(personService.getPersonByName("Mike")).thenReturn(null);

        this.mockMvc.perform(get("/api/v1/person/name?name=Mike")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource not found"));

        // If a name is passed, that is not provided by the PersonService it should also return a 404
        this.mockMvc.perform(get("/api/v1/person/name?name=Mickey")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource not found"));
    }

    @Test
    void getPersonByIdShouldReturnPerson() throws Exception {
        UUID id = UUID.randomUUID();
        Person returnPerson = new Person("Mike");
        returnPerson.setId(id);

        when(personService.getPersonById(id)).thenReturn(returnPerson);

        this.mockMvc.perform(get("/api/v1/person/"+id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mike"))
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void getPersonByIdShouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(personService.getPersonById(id)).thenReturn(null);

        this.mockMvc.perform(get("/api/v1/person/"+id)).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource not found"));
    }
}