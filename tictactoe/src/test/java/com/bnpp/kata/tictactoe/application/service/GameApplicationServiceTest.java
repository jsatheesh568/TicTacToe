package com.bnpp.kata.tictactoe.application.service;


import com.bnpp.kata.tictactoe.domain.model.Game;
import com.bnpp.kata.tictactoe.domain.model.GameStatus;
import com.bnpp.kata.tictactoe.domain.model.Mark;
import com.bnpp.kata.tictactoe.domain.model.PlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameApplicationServiceTest {

    private GameApplicationService gameApplicationService;

    @BeforeEach
    void setUp() {
        gameApplicationService = new GameApplicationService();
    }

    @Test
    void shouldCreateAndStoreNewGame() {

        String gameId = gameApplicationService.startGame(
                "Alice",
                PlayerType.HUMAN,
                "Bob",
                PlayerType.HUMAN
        );

        assertNotNull(gameId);

        Game game = gameApplicationService.getGame(gameId);

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals("Alice", game.getCurrentPlayer().getName());
    }

    @Test
    void shouldCreateGameWithComputerPlayer() {

        String gameId = gameApplicationService.startGame(
                "Alice",
                PlayerType.HUMAN,
                "Computer",
                PlayerType.COMPUTER
        );

        Game game = gameApplicationService.getGame(gameId);

        assertEquals(
                PlayerType.COMPUTER,
                game.getPlayer(Mark.O).getType()
        );
    }

    @Test
    void shouldDefaultMissingPlayerTypesToHuman() {

        String gameId = gameApplicationService.startGame(
                "Alice",
                null,
                "Bob",
                null
        );

        Game game = gameApplicationService.getGame(gameId);

        assertEquals(
                PlayerType.HUMAN,
                game.getPlayer(Mark.X).getType()
        );

        assertEquals(
                PlayerType.HUMAN,
                game.getPlayer(Mark.O).getType()
        );
    }

    @Test
    void shouldThrowExceptionWhenGameDoesNotExist() {

        assertThrows(
                IllegalArgumentException.class,
                () -> gameApplicationService.getGame("unknown-game-id")
        );
    }

    @Test
    void shouldCreateUniqueGameIds() {

        String firstGameId = gameApplicationService.startGame(
                "Alice",
                PlayerType.HUMAN,
                "Bob",
                PlayerType.HUMAN
        );

        String secondGameId = gameApplicationService.startGame(
                "Charlie",
                PlayerType.HUMAN,
                "David",
                PlayerType.HUMAN
        );

        assertNotEquals(firstGameId, secondGameId);
    }
}