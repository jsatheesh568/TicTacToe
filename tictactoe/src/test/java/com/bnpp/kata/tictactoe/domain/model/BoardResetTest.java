
package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardResetTest {

    @Test
    void shouldResetAllCellsWhenBoardContainsMarks() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 1, Mark.O);
        board.placeMark(2, 2, Mark.X);

        board.reset();

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertTrue(board.isCellEmpty(row, column));
                assertNull(board.getMarkAt(row, column));
            }
        }
    }

    @Test
    void shouldKeepBoardEmptyWhenResettingAnEmptyBoard() {
        Board board = new Board();

        board.reset();

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertTrue(board.isCellEmpty(row, column));
            }
        }
    }

    @Test
    void shouldAllowPlacingMarksAfterBoardIsReset() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.reset();

        board.placeMark(0, 0, Mark.O);

        assertNull(board.getMarkAt(0, 1));
        org.junit.jupiter.api.Assertions.assertEquals(
                Mark.O,
                board.getMarkAt(0, 0)
        );
    }
}
