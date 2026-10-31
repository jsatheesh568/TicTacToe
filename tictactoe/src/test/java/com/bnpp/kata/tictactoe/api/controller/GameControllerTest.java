
package com.bnpp.kata.tictactoe.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateNewGameSuccessfully() throws Exception {

        String request = """
                {
                    "playerXName": "Alice",
                    "playerOName": "Bob",
                    "playerXType": "HUMAN",
                    "playerOType": "HUMAN"
                }
                """;

        mockMvc.perform(
                        post("/api/games")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameId", not(isEmptyOrNullString())))
                .andExpect(jsonPath("$.playerXName").value("Alice"))
                .andExpect(jsonPath("$.playerXMark").value("X"))
                .andExpect(jsonPath("$.playerXType").value("HUMAN"))
                .andExpect(jsonPath("$.playerOName").value("Bob"))
                .andExpect(jsonPath("$.playerOMark").value("O"))
                .andExpect(jsonPath("$.playerOType").value("HUMAN"))
                .andExpect(jsonPath("$.currentPlayerName").value("Alice"))
                .andExpect(jsonPath("$.currentMark").value("X"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void shouldCreateGameWithComputerPlayer() throws Exception {

        String request = """
                {
                    "playerXName": "Alice",
                    "playerOName": "Computer",
                    "playerXType": "HUMAN",
                    "playerOType": "COMPUTER"
                }
                """;

        mockMvc.perform(
                        post("/api/games")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerXType").value("HUMAN"))
                .andExpect(jsonPath("$.playerOType").value("COMPUTER"));
    }

    @Test
    void shouldGenerateDifferentIdsForDifferentGames() throws Exception {

        String request = """
                {
                    "playerXName": "Alice",
                    "playerOName": "Bob"
                }
                """;

        String firstResponse = mockMvc.perform(
                        post("/api/games")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String secondResponse = mockMvc.perform(
                        post("/api/games")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode firstJson = objectMapper.readTree(firstResponse);
        JsonNode secondJson = objectMapper.readTree(secondResponse);

        String firstGameId = firstJson.get("gameId").asString();
        String secondGameId = secondJson.get("gameId").asString();

        assertNotEquals(firstGameId, secondGameId);
    }
}
