
package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

        game.play(0, 0); // X
        game.play(1, 0); // O
        game.play(0, 1); // X
        game.play(1, 1); // O
        game.play(0, 2); // X wins

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldSetGameStatusToWonWhenPlayerWinsVertically() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(0, 1); // O
        game.play(1, 0); // X
        game.play(1, 1); // O
        game.play(2, 0); // X wins

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldSetGameStatusToWonWhenPlayerWinsDiagonally() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(0, 1); // O
        game.play(1, 1); // X
        game.play(0, 2); // O
        game.play(2, 2); // X wins

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldKeepGameInProgressWhenNoPlayerHasWon() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(0, 1); // O
        game.play(0, 2); // X
        game.play(1, 1); // O

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldSetGameStatusToDrawWhenBoardIsFullWithoutWinner() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(1, 1); // O
        game.play(0, 2); // X
        game.play(0, 1); // O
        game.play(1, 0); // X
        game.play(2, 0); // O
        game.play(1, 2); // X
        game.play(2, 2); // O
        game.play(2, 1); // X

        assertEquals(GameStatus.DRAW, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldRejectMoveWhenGameIsAlreadyWon() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(1, 0); // O
        game.play(0, 1); // X
        game.play(1, 1); // O
        game.play(0, 2); // X wins

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

        game.play(0, 0); // X
        game.play(1, 1); // O
        game.play(0, 2); // X
        game.play(0, 1); // O
        game.play(1, 0); // X
        game.play(2, 0); // O
        game.play(1, 2); // X
        game.play(2, 2); // O
        game.play(2, 1); // X draws

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
}
