package com.micray.springdemo.api;

import com.micray.springdemo.SecurityConfiguration;
import com.micray.springdemo.model.Person;
import com.micray.springdemo.service.PersonService;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SecurityConfiguration.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


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
                .andExpect(jsonPath("$.message").value("Resource not found"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void getPersonByNameShouldReturnNotFound() throws Exception {
        when(personService.getPersonByName("Mike")).thenReturn(null);

        this.mockMvc.perform(get("/api/v1/person/name?name=Mike")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource not found"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        // If a name is passed, that is not provided by the PersonService it should also return a 404
        this.mockMvc.perform(get("/api/v1/person/name?name=Mickey")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource not found"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void getPersonByIdShouldReturnPerson() throws Exception {
        String id = "123";
        Person returnPerson = new Person("Mike");
        returnPerson.setId(id);

        when(personService.getPersonById(id)).thenReturn(returnPerson);

        this.mockMvc.perform(get("/api/v1/person/"+id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mike"))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getPersonByIdShouldReturnNotFound() throws Exception {
        String id = "123";

        when(personService.getPersonById(id)).thenReturn(null);

        this.mockMvc.perform(get("/api/v1/person/"+id)).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource not found"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void getAllPeopleShouldReturnPeople() throws Exception{

        Person person1 = new Person("Mike");
        Person person2 = new Person("James");
        List<Person> personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);

        when(personService.getAllPeople()).thenReturn(personList);

        this.mockMvc.perform(get("/api/v1/person/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Mike"))
                .andExpect(jsonPath("$[1].name").value("James"));
    }

    @Test
    void getAllPeopleShouldReturnNotFound() throws Exception{

        when(personService.getAllPeople()).thenReturn(null);

        this.mockMvc.perform(get("/api/v1/person/")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resources not found"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void deletePersonByIdShouldReturnNoContent() throws Exception {
        String id = "123";
        when(personService.deletePersonById(id)).thenReturn(true);

        this.mockMvc.perform(delete("/api/v1/person/"+id).with(httpBasic("user","password")))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePersonByIdShouldReturnServerError() throws Exception {
        String id = "123";
        when(personService.deletePersonById(id)).thenReturn(false);

        this.mockMvc.perform(delete("/api/v1/person/"+id).with(httpBasic("user","password")))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}