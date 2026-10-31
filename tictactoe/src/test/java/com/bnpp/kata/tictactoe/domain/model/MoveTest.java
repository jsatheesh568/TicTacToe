
package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveTest {

    @Test
    void shouldStorePlayerMarkAndPosition() {
        Move move = new Move(Mark.X, 1, 2);

        assertEquals(Mark.X, move.getMark());
        assertEquals(1, move.getRow());
        assertEquals(2, move.getColumn());
    }

    @Test
    void shouldAllowPlayerOAsMoveMark() {
        Move move = new Move(Mark.O, 2, 1);

        assertEquals(Mark.O, move.getMark());
        assertEquals(2, move.getRow());
        assertEquals(1, move.getColumn());
    }

    @Test
    void shouldRejectNullPlayerMark() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Move(null, 0, 0)
        );
    }
}
