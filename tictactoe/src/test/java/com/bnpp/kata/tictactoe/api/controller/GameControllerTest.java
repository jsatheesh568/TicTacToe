package com.bnpp.kata.tictactoe.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    // ---------------------------------------------------------
    // POST /api/games
    // ---------------------------------------------------------

    @Test
    void shouldCreateNewGameWithHumanPlayers() throws Exception {

        String request = """
                {
                    "playerXName": "Alice",
                    "playerXType": "HUMAN",
                    "playerOName": "Bob",
                    "playerOType": "HUMAN"
                }
                """;

        mockMvc.perform(
                        post("/api/games")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameId").isNotEmpty())
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
    void shouldCreateNewGameWithComputerPlayer() throws Exception {

        String request = """
                {
                    "playerXName": "Alice",
                    "playerXType": "HUMAN",
                    "playerOName": "Computer",
                    "playerOType": "COMPUTER"
                }
                """;

        mockMvc.perform(
                        post("/api/games")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerXName").value("Alice"))
                .andExpect(jsonPath("$.playerOName").value("Computer"))
                .andExpect(jsonPath("$.playerOType").value("COMPUTER"))
                .andExpect(jsonPath("$.currentPlayerName").value("Alice"));
    }

    // ---------------------------------------------------------
    // POST /api/games/{gameId}/moves
    // ---------------------------------------------------------

    @Test
    void shouldMakeMoveForHumanPlayers() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN",
                "Bob", "HUMAN"
        );

        String request = """
                {
                    "row": 0,
                    "column": 0
                }
                """;

        mockMvc.perform(
                        post("/api/games/{gameId}/moves", gameId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(gameId))
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.currentPlayerName").value("Bob"))
                .andExpect(jsonPath("$.currentMark").value("O"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void shouldAutomaticallyMakeComputerMoveAfterHumanMove()
            throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN",
                "Computer", "COMPUTER"
        );

        String request = """
                {
                    "row": 0,
                    "column": 0
                }
                """;

        mockMvc.perform(
                        post("/api/games/{gameId}/moves", gameId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.currentPlayerName").value("Alice"))
                .andExpect(jsonPath("$.currentMark").value("X"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void shouldNotMakeComputerMoveWhenHumanWins() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN",
                "Bob", "HUMAN"
        );

        makeMove(gameId, 0, 0);
        makeMove(gameId, 1, 0);
        makeMove(gameId, 0, 1);
        makeMove(gameId, 1, 1);

        mockMvc.perform(
                        post("/api/games/{gameId}/moves", gameId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "row": 0,
                                            "column": 2
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("WON"))
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.board[0][1]").value("X"))
                .andExpect(jsonPath("$.board[0][2]").value("X"));
    }

    // ---------------------------------------------------------
    // GET /api/games/{gameId}
    // ---------------------------------------------------------

    @Test
    void shouldReturnInitialGameState() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN",
                "Bob", "HUMAN"
        );

        mockMvc.perform(
                        get("/api/games/{gameId}", gameId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(gameId))
                .andExpect(jsonPath("$.playerXName").value("Alice"))
                .andExpect(jsonPath("$.playerXMark").value("X"))
                .andExpect(jsonPath("$.playerXType").value("HUMAN"))
                .andExpect(jsonPath("$.playerOName").value("Bob"))
                .andExpect(jsonPath("$.playerOMark").value("O"))
                .andExpect(jsonPath("$.playerOType").value("HUMAN"))
                .andExpect(jsonPath("$.currentPlayerName").value("Alice"))
                .andExpect(jsonPath("$.currentMark").value("X"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.board.length()").value(3))
                .andExpect(jsonPath("$.board[0][0]").value(nullValue()))
                .andExpect(jsonPath("$.moveHistory").isArray())
                .andExpect(jsonPath("$.moveHistory").isEmpty());
    }

    @Test
    void shouldReturnUpdatedGameStateAfterMoves() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN",
                "Computer", "COMPUTER"
        );

        makeMove(gameId, 0, 0);

        MvcResult result = mockMvc.perform(
                        get("/api/games/{gameId}", gameId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(gameId))
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.moveHistory.length()").value(2))
                .andExpect(jsonPath("$.moveHistory[0].mark").value("X"))
                .andExpect(jsonPath("$.moveHistory[0].row").value(0))
                .andExpect(jsonPath("$.moveHistory[0].column").value(0))
                .andExpect(jsonPath("$.moveHistory[1].mark").value("O"))
                .andExpect(jsonPath("$.currentPlayerName").value("Alice"))
                .andExpect(jsonPath("$.currentMark").value("X"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andReturn();

        // Confirm the computer selected a valid board position.
        JsonNode response = objectMapper.readTree(
                result.getResponse().getContentAsString()
        );

        JsonNode computerMove = response
                .get("moveHistory")
                .get(1);

        int row = computerMove.get("row").asInt();
        int column = computerMove.get("column").asInt();

        org.junit.jupiter.api.Assertions.assertTrue(
                row >= 0 && row < 3
        );

        org.junit.jupiter.api.Assertions.assertTrue(
                column >= 0 && column < 3
        );
    }

    @Test
    void shouldReturnGameStateWithCorrectWinnerStatus() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN",
                "Bob", "HUMAN"
        );

        makeMove(gameId, 0, 0); // X
        makeMove(gameId, 1, 0); // O
        makeMove(gameId, 0, 1); // X
        makeMove(gameId, 1, 1); // O
        makeMove(gameId, 0, 2); // X wins

        mockMvc.perform(
                        get("/api/games/{gameId}", gameId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("WON"))
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.board[0][1]").value("X"))
                .andExpect(jsonPath("$.board[0][2]").value("X"))
                .andExpect(jsonPath("$.moveHistory.length()").value(5));
    }

    // ---------------------------------------------------------
    // Shared helper methods
    // ---------------------------------------------------------

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
                        post("/api/games")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode response = objectMapper.readTree(
                result.getResponse().getContentAsString()
        );

        return response.get("gameId").asText();
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
                        post("/api/games/{gameId}/moves", gameId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk());
    }
}