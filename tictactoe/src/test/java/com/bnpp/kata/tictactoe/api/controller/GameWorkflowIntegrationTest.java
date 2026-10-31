
package com.bnpp.kata.tictactoe.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameWorkflowIntegrationTest {

    private static final String GAMES_API = "/api/games";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // --------------------------------------------------
    // Complete human vs human workflow - winning game
    // --------------------------------------------------

    @Test
    void shouldCompleteHumanGameAndDeclareWinner() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN",
                "Bob", "HUMAN"
        );

        makeMove(gameId, 0, 0); // X
        makeMove(gameId, 1, 0); // O
        makeMove(gameId, 0, 1); // X
        makeMove(gameId, 1, 1); // O
        makeMove(gameId, 0, 2); // X wins

        mockMvc.perform(get(GAMES_API + "/{gameId}", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(gameId))
                .andExpect(jsonPath("$.status").value("WON"))
                .andExpect(jsonPath("$.currentPlayerName").value("Alice"))
                .andExpect(jsonPath("$.currentMark").value("X"))
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.board[0][1]").value("X"))
                .andExpect(jsonPath("$.board[0][2]").value("X"))
                .andExpect(jsonPath("$.moveHistory.length()").value(5));
    }

    // --------------------------------------------------
    // Complete human vs human workflow - draw game
    // --------------------------------------------------


    @Test
    void shouldCompleteHumanGameAndDeclareDraw() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN",
                "Bob", "HUMAN"
        );

        makeMove(gameId, 0, 0); // X
        makeMove(gameId, 1, 1); // O
        makeMove(gameId, 2, 2); // X
        makeMove(gameId, 0, 2); // O
        makeMove(gameId, 2, 0); // X
        makeMove(gameId, 1, 0); // O
        makeMove(gameId, 0, 1); // X
        makeMove(gameId, 2, 1); // O
        makeMove(gameId, 1, 2); // X

        mockMvc.perform(get(GAMES_API + "/{gameId}", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DRAW"))
                .andExpect(jsonPath("$.moveHistory.length()").value(9))
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.board[0][1]").value("X"))
                .andExpect(jsonPath("$.board[0][2]").value("O"))
                .andExpect(jsonPath("$.board[1][0]").value("O"))
                .andExpect(jsonPath("$.board[1][1]").value("O"))
                .andExpect(jsonPath("$.board[1][2]").value("X"))
                .andExpect(jsonPath("$.board[2][0]").value("X"))
                .andExpect(jsonPath("$.board[2][1]").value("O"))
                .andExpect(jsonPath("$.board[2][2]").value("X"));
    }


    // --------------------------------------------------
    // Human vs computer workflow
    // --------------------------------------------------

    @Test
    void shouldAutomaticallyMakeComputerMoveAfterHumanMove()
            throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN",
                "Computer", "COMPUTER"
        );

        makeMove(gameId, 0, 0);

        mockMvc.perform(get(GAMES_API + "/{gameId}", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.currentPlayerName").value("Alice"))
                .andExpect(jsonPath("$.currentMark").value("X"))
                .andExpect(jsonPath("$.moveHistory.length()").value(2))
                .andExpect(jsonPath("$.board[0][0]").value("X"));

        MvcResult result = mockMvc.perform(
                        get(GAMES_API + "/{gameId}", gameId)
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode response = objectMapper.readTree(
                result.getResponse().getContentAsString()
        );

        JsonNode board = response.get("board");

        int occupiedCells = 0;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (!board.get(row).get(column).isNull()) {
                    occupiedCells++;
                }
            }
        }

        assertEquals(2, occupiedCells);
    }

    // --------------------------------------------------
    // Invalid move must not change game state
    // --------------------------------------------------

    @Test
    void shouldPreserveGameStateWhenMoveIsInvalid() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN",
                "Bob", "HUMAN"
        );

        makeMove(gameId, 0, 0);

        mockMvc.perform(
                        post(GAMES_API + "/{gameId}/moves", gameId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "row": 0,
                                            "column": 0
                                        }
                                        """)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error")
                        .value("INVALID_GAME_OPERATION"));

        mockMvc.perform(get(GAMES_API + "/{gameId}", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.moveHistory.length()").value(1))
                .andExpect(jsonPath("$.currentPlayerName").value("Bob"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    // --------------------------------------------------
    // Test helpers
    // --------------------------------------------------

    private String startGame(
            String playerXName,
            String playerXType,
            String playerOName,
            String playerOType
    ) throws Exception {

        String request = """
                {
                    "playerXName": "%s",
                    "playerXType": "%s",
                    "playerOName": "%s",
                    "playerOType": "%s"
                }
                """.formatted(
                playerXName,
                playerXType,
                playerOName,
                playerOType
        );

        MvcResult result = mockMvc.perform(
                        post(GAMES_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode response = objectMapper.readTree(
                result.getResponse().getContentAsString()
        );

        JsonNode gameIdNode = response.get("gameId");

        assertNotNull(gameIdNode);

        return gameIdNode.asString();
    }

    private void makeMove(
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

        mockMvc.perform(
                        post(GAMES_API + "/{gameId}/moves", gameId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk());
    }
}
