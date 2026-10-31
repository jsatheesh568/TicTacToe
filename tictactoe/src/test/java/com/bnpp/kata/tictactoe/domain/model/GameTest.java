package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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