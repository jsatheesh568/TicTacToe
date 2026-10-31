
package com.bnpp.kata.tictactoe.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldExposeOpenApiSpecification() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title")
                        .value("Tic Tac Toe Game API"))
                .andExpect(jsonPath("$.info.version")
                        .value("1.0.0"))
                .andExpect(jsonPath("$.paths['/api/games'].post")
                        .exists())
                .andExpect(jsonPath(
                        "$.paths['/api/games/{gameId}/moves'].post"
                ).exists())
                .andExpect(jsonPath(
                        "$.paths['/api/games/{gameId}'].get"
                ).exists());
    }
}
