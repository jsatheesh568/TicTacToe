
package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {

    @Test
    void shouldStartGameWithPlayerX() {
        Game game = new Game();

        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldStartGameWithInProgressStatus() {
        Game game = new Game();

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldPlaceXAndSwitchTurnToO() {
        Game game = new Game();

        game.play(0, 0);

        assertEquals(Mark.X, game.getMarkAt(0, 0));
        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldPlaceOAndSwitchTurnToX() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        assertEquals(Mark.O, game.getMarkAt(1, 1));
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldSetGameStatusToWonWhenPlayerWinsHorizontally() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldSetGameStatusToWonWhenPlayerWinsVertically() {
        Game game = new Game();

        game.play(0, 0);
        game.play(0, 1);
        game.play(1, 0);
        game.play(1, 1);
        game.play(2, 0);

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldSetGameStatusToWonWhenPlayerWinsDiagonally() {
        Game game = new Game();

        game.play(0, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);
        game.play(2, 2);

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldKeepGameInProgressWhenNoPlayerHasWon() {
        Game game = new Game();

        game.play(0, 0);
        game.play(0, 1);
        game.play(0, 2);
        game.play(1, 1);

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldSetGameStatusToDrawWhenBoardIsFullWithoutWinner() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);
        game.play(0, 2);
        game.play(0, 1);
        game.play(1, 0);
        game.play(2, 0);
        game.play(1, 2);
        game.play(2, 2);
        game.play(2, 1);

        assertEquals(GameStatus.DRAW, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldRejectMoveWhenGameIsAlreadyWon() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> game.play(2, 2)
        );

        assertEquals(
                "Game has already finished with status: WON",
                exception.getMessage()
        );

        assertNull(game.getMarkAt(2, 2));
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.WON, game.getStatus());
    }

    @Test
    void shouldRejectMoveWhenGameIsAlreadyDrawn() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);
        game.play(0, 2);
        game.play(0, 1);
        game.play(1, 0);
        game.play(2, 0);
        game.play(1, 2);
        game.play(2, 2);
        game.play(2, 1);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> game.play(0, 0)
        );

        assertEquals(
                "Game has already finished with status: DRAW",
                exception.getMessage()
        );

        assertEquals(Mark.X, game.getMarkAt(0, 0));
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.DRAW, game.getStatus());
    }

    @Test
    void shouldRestartGameAfterPlayerWins() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);

        assertEquals(GameStatus.WON, game.getStatus());

        game.restart();

        assertGameIsInInitialState(game);
    }

    @Test
    void shouldRestartGameAfterDraw() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);
        game.play(0, 2);
        game.play(0, 1);
        game.play(1, 0);
        game.play(2, 0);
        game.play(1, 2);
        game.play(2, 2);
        game.play(2, 1);

        assertEquals(GameStatus.DRAW, game.getStatus());

        game.restart();

        assertGameIsInInitialState(game);
    }

    @Test
    void shouldRestartGameWhileGameIsInProgress() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        assertEquals(Mark.X, game.getCurrentMark());

        game.restart();

        assertGameIsInInitialState(game);
    }

    @Test
    void shouldAllowPlayingAgainAfterRestart() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);

        game.restart();

        game.play(2, 2);

        assertEquals(Mark.X, game.getMarkAt(2, 2));
        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldRestartTheSameGameInstance() {
        Game game = new Game();

        game.play(0, 0);

        Game originalGame = game;

        game.restart();

        assertSame(originalGame, game);
    }

    @Test
    void shouldNotChangeTurnWhenPositionIsAlreadyOccupied() {
        Game game = new Game();

        game.play(0, 0);

        assertThrows(
                IllegalStateException.class,
                () -> game.play(0, 0)
        );

        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldNotChangeTurnWhenPositionIsInvalid() {
        Game game = new Game();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> game.play(3, 0)
        );

        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    private void assertGameIsInInitialState(Game game) {
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertNull(game.getMarkAt(row, column));
            }
        }
    }
}
