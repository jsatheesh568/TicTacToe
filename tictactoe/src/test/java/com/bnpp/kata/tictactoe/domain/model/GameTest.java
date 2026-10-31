
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
    }

    @Test
    void shouldPlaceOAndSwitchTurnToX() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        assertEquals(Mark.O, game.getMarkAt(1, 1));
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
    }

    @Test
    void shouldNotChangeTurnWhenPositionIsInvalid() {
        Game game = new Game();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> game.play(3, 0)
        );

        assertEquals(Mark.X, game.getCurrentMark());
    }
}
