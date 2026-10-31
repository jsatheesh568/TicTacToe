package com.bnpp.kata.tictactoe.application.service;

import com.bnpp.kata.tictactoe.application.exception.GameNotFoundException;
import com.bnpp.kata.tictactoe.application.exception.InvalidGameOperationException;
import com.bnpp.kata.tictactoe.domain.model.Game;
import com.bnpp.kata.tictactoe.domain.model.GameStatus;
import com.bnpp.kata.tictactoe.domain.model.Mark;
import com.bnpp.kata.tictactoe.domain.model.PlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameMoveApplicationServiceTest {

    private GameApplicationService service;

    @BeforeEach
    void setUp() {
        service = new GameApplicationService();
    }

    @Test
    void shouldPlayHumanMoveAndSwitchToNextHumanPlayer() {
        GameSession session = service.startGame(
                "Alice",
                PlayerType.HUMAN,
                "Bob",
                PlayerType.HUMAN
        );

        Game game = service.playMove(
                session.getGameId(),
                0,
                0
        );

        assertEquals(Mark.X, game.getMarkAt(0, 0));
        assertNull(game.getMarkAt(0, 1));
        assertEquals("Bob", game.getCurrentPlayer().getName());
        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldAutomaticallyPlayComputerMoveAfterHumanMove() {
        GameSession session = service.startGame(
                "Alice",
                PlayerType.HUMAN,
                "Computer",
                PlayerType.COMPUTER
        );

        Game game = service.playMove(
                session.getGameId(),
                0,
                0
        );

        assertEquals(Mark.X, game.getMarkAt(0, 0));

        // The computer must have occupied one other cell.
        int occupiedCells = 0;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (game.getMarkAt(row, column) != null) {
                    occupiedCells++;
                }
            }
        }

        assertEquals(2, occupiedCells);
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals("Alice", game.getCurrentPlayer().getName());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldNotPlayComputerMoveWhenHumanWins() {
        GameSession session = service.startGame(
                "Alice",
                PlayerType.HUMAN,
                "Computer",
                PlayerType.COMPUTER
        );

        Game game = session.getGame();

        // Arrange a position where X can win on the next move.
        game.play(0, 0); // X
        game.play(1, 0); // O
        game.play(0, 1); // X
        game.play(1, 1); // O

        // It is now X's turn. This move wins the game.
        Game result = service.playMove(
                session.getGameId(),
                0,
                2
        );

        assertEquals(Mark.X, result.getMarkAt(0, 2));
        assertEquals(GameStatus.WON, result.getStatus());

        // The computer must not make another move after the game ends.
        int occupiedCells = 0;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (result.getMarkAt(row, column) != null) {
                    occupiedCells++;
                }
            }
        }

        assertEquals(5, occupiedCells);
    }

    @Test
    void shouldRejectMoveWhenGameDoesNotExist() {
        assertThrows(
                GameNotFoundException.class,
                () -> service.playMove("invalid-game-id", 0, 0)
        );
    }

    @Test
    void shouldRejectMoveWhenItIsComputerPlayersTurn() {
        GameSession session = service.startGame(
                "Computer",
                PlayerType.COMPUTER,
                "Alice",
                PlayerType.HUMAN
        );

        assertThrows(
                InvalidGameOperationException.class,
                () -> service.playMove(session.getGameId(), 0, 0)
        );
    }
}