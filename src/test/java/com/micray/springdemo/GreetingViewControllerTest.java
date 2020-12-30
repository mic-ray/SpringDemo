package com.micray.springdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GreetingViewController.class)
class GreetingViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void greetingShouldReturnCustomModelAndView() throws Exception {
        this.mockMvc
                .perform(get("/greeting?name=Mike")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("greeting"))
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attribute("name", "Mike"));
    }

    @Test
    void greetingShouldReturnDefaultModelAndView() throws Exception {
        this.mockMvc
                .perform(get("/greeting")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("greeting"))
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attribute("name", "World"));
    }
}