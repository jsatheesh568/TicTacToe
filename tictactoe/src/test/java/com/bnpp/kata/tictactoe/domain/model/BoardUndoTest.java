
package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardUndoTest {

    @Test
    void shouldRemoveMarkFromSpecifiedPosition() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 1, Mark.O);

        board.removeMark(1, 1);

        assertTrue(board.isCellEmpty(1, 1));
        assertNull(board.getMarkAt(1, 1));
        assertEquals(Mark.X, board.getMarkAt(0, 0));
    }

    @Test
    void shouldAllowPlacingAnotherMarkAfterRemovingExistingMark() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.removeMark(0, 0);

        board.placeMark(0, 0, Mark.O);

        assertEquals(Mark.O, board.getMarkAt(0, 0));
    }

    @Test
    void shouldNotChangeOtherCellsWhenRemovingMark() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 1, Mark.O);
        board.placeMark(2, 2, Mark.X);

        board.removeMark(1, 1);

        assertEquals(Mark.X, board.getMarkAt(0, 0));
        assertNull(board.getMarkAt(1, 1));
        assertEquals(Mark.X, board.getMarkAt(2, 2));
    }

    @Test
    void shouldValidatePositionWhenRemovingMark() {
        Board board = new Board();

        org.junit.jupiter.api.Assertions.assertThrows(
                IndexOutOfBoundsException.class,
                () -> board.removeMark(3, 0)
        );
    }
}
