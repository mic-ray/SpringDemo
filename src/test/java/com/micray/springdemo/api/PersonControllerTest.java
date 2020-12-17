package com.micray.springdemo.api;

import com.micray.springdemo.model.Person;
import com.micray.springdemo.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
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

        this.mockMvc.perform(get("/api/v1/person/name?name=Mike"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mike"));
    }
}