package com.example.restservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class GreetingTest {

    @Autowired
    MockMvc mockMvc; //@AutoConfigureMockMvc에 의해 받을 수 있음

    @Test
    void greeting() throws Exception {
        mockMvc.perform(get("/greeting"))
                .andDo(print()) //http와 controller간에 작업하는 모든것을 출력 받을 수 있다.
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, World!"));
    }

    @Test
    void greetingWithName() throws Exception {
        mockMvc.perform(get("/greeting").param("name", "Spring"))
                .andDo(print()) //http와 controller간에 작업하는 모든것을 출력 받을 수 있다.
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, Spring!"));
    }
}
