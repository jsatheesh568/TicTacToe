package com.bnpp.kata.tictactoe.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameMoveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldMakeMoveForHumanVsHumanGame() throws Exception {
        String gameId = createGame(
                "Alice",
                "Bob",
                "HUMAN",
                "HUMAN"
        );

        String moveRequest = """
                {
                    "row": 0,
                    "column": 0
                }
                """;

        mockMvc.perform(
                        post("/api/games/{gameId}/moves", gameId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(moveRequest)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(gameId))
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.currentPlayerName").value("Bob"))
                .andExpect(jsonPath("$.currentMark").value("O"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void shouldAutomaticallyMakeComputerMove() throws Exception {
        String gameId = createGame(
                "Alice",
                "Computer",
                "HUMAN",
                "COMPUTER"
        );

        String moveRequest = """
                {
                    "row": 0,
                    "column": 0
                }
                """;

        String response = mockMvc.perform(
                        post("/api/games/{gameId}/moves", gameId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(moveRequest)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(gameId))
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode responseJson = objectMapper.readTree(response);
        JsonNode board = responseJson.get("board");

        int occupiedCells = 0;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (!board.get(row).get(column).isNull()) {
                    occupiedCells++;
                }
            }
        }

        assertEquals(2, occupiedCells);

        // The computer has played, so it is Alice's turn again.
        assertEquals(
                "Alice",
                responseJson.get("currentPlayerName").asString()
        );

        assertEquals(
                "X",
                responseJson.get("currentMark").asString()
        );
    }

    @Test
    void shouldReturnUpdatedBoardAfterMultipleHumanMoves() throws Exception {
        String gameId = createGame(
                "Alice",
                "Bob",
                "HUMAN",
                "HUMAN"
        );

        makeMove(gameId, 0, 0);
        makeMove(gameId, 1, 1);

        String response = makeMove(gameId, 0, 1);

        JsonNode responseJson = objectMapper.readTree(response);
        JsonNode board = responseJson.get("board");

        assertEquals("X", board.get(0).get(0).asString());
        assertEquals("X", board.get(0).get(1).asString());
        assertEquals("O", board.get(1).get(1).asString());
        assertEquals("O", responseJson.get("currentMark").asString());
    }

    private String createGame(
            String playerXName,
            String playerOName,
            String playerXType,
            String playerOType
    ) throws Exception {
        String request = """
                {
                    "playerXName": "%s",
                    "playerOName": "%s",
                    "playerXType": "%s",
                    "playerOType": "%s"
                }
                """.formatted(
                playerXName,
                playerOName,
                playerXType,
                playerOType
        );

        String response = mockMvc.perform(
                        post("/api/games")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode responseJson = objectMapper.readTree(response);

        String gameId = responseJson.get("gameId").asString();

        assertNotNull(gameId);

        return gameId;
    }

    private String makeMove(
            String gameId,
            int row,
            int column
    ) throws Exception {
        String request = """
                {
                    "row": %d,
                    "column": %d
                }
                """.formatted(row, column);

        return mockMvc.perform(
                        post("/api/games/{gameId}/moves", gameId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}