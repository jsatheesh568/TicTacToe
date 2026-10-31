
package com.bnpp.kata.tictactoe.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    private static final String GAMES_API = "/api/games";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // --------------------------------------------------
    // POST /api/games
    // --------------------------------------------------

    @Test
    void shouldCreateNewGame() throws Exception {

        String request = """
                {
                    "playerXName": "Alice",
                    "playerXType": "HUMAN",
                    "playerOName": "Bob",
                    "playerOType": "HUMAN"
                }
                """;

        mockMvc.perform(post(GAMES_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameId").exists())
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
                    "playerXType": "HUMAN",
                    "playerOName": "Computer",
                    "playerOType": "COMPUTER"
                }
                """;

        mockMvc.perform(post(GAMES_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerXType").value("HUMAN"))
                .andExpect(jsonPath("$.playerOType").value("COMPUTER"));
    }

    // --------------------------------------------------
    // POST /api/games/{gameId}/moves
    // --------------------------------------------------

    @Test
    void shouldMakeMoveAndReturnUpdatedGameState() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN", "Bob", "HUMAN"
        );

        String request = """
                {
                    "row": 0,
                    "column": 1
                }
                """;

        mockMvc.perform(post(GAMES_API + "/{gameId}/moves", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(gameId))
                .andExpect(jsonPath("$.board[0][1]").value("X"))
                .andExpect(jsonPath("$.currentPlayerName").value("Bob"))
                .andExpect(jsonPath("$.currentMark").value("O"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    // --------------------------------------------------
    // GET /api/games/{gameId}
    // --------------------------------------------------

    @Test
    void shouldRetrieveGameState() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN", "Bob", "HUMAN"
        );

        makeMove(gameId, 0, 0);

        mockMvc.perform(get(GAMES_API + "/{gameId}", gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(gameId))
                .andExpect(jsonPath("$.playerXName").value("Alice"))
                .andExpect(jsonPath("$.playerXMark").value("X"))
                .andExpect(jsonPath("$.playerOName").value("Bob"))
                .andExpect(jsonPath("$.playerOMark").value("O"))
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.currentPlayerName").value("Bob"))
                .andExpect(jsonPath("$.currentMark").value("O"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.moveHistory.length()").value(1))
                .andExpect(jsonPath("$.moveHistory[0].mark").value("X"))
                .andExpect(jsonPath("$.moveHistory[0].row").value(0))
                .andExpect(jsonPath("$.moveHistory[0].column").value(0));
    }

    // --------------------------------------------------
    // Request validation - POST /api/games
    // --------------------------------------------------

    @Test
    void shouldRejectGameCreationWhenPlayerXNameIsBlank() throws Exception {

        String request = """
                {
                    "playerXName": " ",
                    "playerXType": "HUMAN",
                    "playerOName": "Bob",
                    "playerOType": "HUMAN"
                }
                """;

        mockMvc.perform(post(GAMES_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectGameCreationWhenPlayerONameIsMissing() throws Exception {

        String request = """
                {
                    "playerXName": "Alice",
                    "playerXType": "HUMAN",
                    "playerOType": "HUMAN"
                }
                """;

        mockMvc.perform(post(GAMES_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectGameCreationWhenPlayerTypeIsMissing() throws Exception {

        String request = """
                {
                    "playerXName": "Alice",
                    "playerOName": "Bob",
                    "playerOType": "HUMAN"
                }
                """;

        mockMvc.perform(post(GAMES_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectGameCreationWhenPlayerONameIsBlank() throws Exception {

        String request = """
                {
                    "playerXName": "Alice",
                    "playerXType": "HUMAN",
                    "playerOName": "",
                    "playerOType": "HUMAN"
                }
                """;

        mockMvc.perform(post(GAMES_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    // --------------------------------------------------
    // Request validation - POST /api/games/{gameId}/moves
    // --------------------------------------------------

    @Test
    void shouldRejectMoveWhenRowIsOutOfRange() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN", "Bob", "HUMAN"
        );

        String request = """
                {
                    "row": 3,
                    "column": 0
                }
                """;

        mockMvc.perform(post(GAMES_API + "/{gameId}/moves", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectMoveWhenColumnIsOutOfRange() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN", "Bob", "HUMAN"
        );

        String request = """
                {
                    "row": 0,
                    "column": -1
                }
                """;

        mockMvc.perform(post(GAMES_API + "/{gameId}/moves", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectMoveWhenRowIsMissing() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN", "Bob", "HUMAN"
        );

        String request = """
                {
                    "column": 1
                }
                """;

        mockMvc.perform(post(GAMES_API + "/{gameId}/moves", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectMoveWhenColumnIsMissing() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN", "Bob", "HUMAN"
        );

        String request = """
                {
                    "row": 1
                }
                """;

        mockMvc.perform(post(GAMES_API + "/{gameId}/moves", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectMoveWhenRowIsNegative() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN", "Bob", "HUMAN"
        );

        String request = """
                {
                    "row": -1,
                    "column": 0
                }
                """;

        mockMvc.perform(post(GAMES_API + "/{gameId}/moves", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    // --------------------------------------------------
    // Unknown game
    // --------------------------------------------------

    @Test
    void shouldReturnNotFoundWhenGameDoesNotExist() throws Exception {

        mockMvc.perform(get(GAMES_API + "/unknown-game-id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("GAME_NOT_FOUND"))
                .andExpect(jsonPath("$.message")
                        .value("Game not found: unknown-game-id"))
                .andExpect(jsonPath("$.path")
                        .value("/api/games/unknown-game-id"));
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

        String response = mockMvc.perform(post(GAMES_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode responseJson = objectMapper.readTree(response);
        String gameId = responseJson.get("gameId").asString();

        assertNotNull(gameId);
        return gameId;
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

        mockMvc.perform(post(GAMES_API + "/{gameId}/moves", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnConflictWhenCellIsAlreadyOccupied() throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN", "Bob", "HUMAN"
        );

        makeMove(gameId, 0, 0);

        String request = """
            {
                "row": 0,
                "column": 0
            }
            """;

        mockMvc.perform(post(GAMES_API + "/{gameId}/moves", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error")
                        .value("INVALID_GAME_OPERATION"))
                .andExpect(jsonPath("$.message")
                        .value("Cell is already occupied"))
                .andExpect(jsonPath("$.path")
                        .value("/api/games/" + gameId + "/moves"));
    }

    @Test
    void shouldReturnConflictWhenMoveIsAttemptedAfterGameIsWon()
            throws Exception {

        String gameId = startGame(
                "Alice", "HUMAN", "Bob", "HUMAN"
        );

        makeMove(gameId, 0, 0); // X
        makeMove(gameId, 1, 0); // O
        makeMove(gameId, 0, 1); // X
        makeMove(gameId, 1, 1); // O
        makeMove(gameId, 0, 2); // X wins

        String request = """
            {
                "row": 2,
                "column": 2
            }
            """;

        mockMvc.perform(post(GAMES_API + "/{gameId}/moves", gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error")
                        .value("INVALID_GAME_OPERATION"));
    }

    @Test
    void shouldReturnBadRequestWithValidationDetailsWhenNameIsBlank()
            throws Exception {

        String request = """
            {
                "playerXName": "",
                "playerXType": "HUMAN",
                "playerOName": "Bob",
                "playerOType": "HUMAN"
            }
            """;

        mockMvc.perform(post(GAMES_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.message")
                        .value(org.hamcrest.Matchers.containsString(
                                "playerXName"
                        )))
                .andExpect(jsonPath("$.path").value(GAMES_API));
    }

    @Test
    void shouldReturnBadRequestForInvalidPlayerType() throws Exception {

        String request = """
            {
                "playerXName": "Alice",
                "playerXType": "INVALID_TYPE",
                "playerOName": "Bob",
                "playerOType": "HUMAN"
            }
            """;

        mockMvc.perform(post(GAMES_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("INVALID_REQUEST"))
                .andExpect(jsonPath("$.path").value(GAMES_API));
    }
}
