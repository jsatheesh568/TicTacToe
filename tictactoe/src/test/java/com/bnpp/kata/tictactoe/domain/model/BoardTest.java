
package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void shouldInitializeAllNineCellsAsEmpty() {
        Board board = new Board();

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertTrue(board.isCellEmpty(row, column));
            }
        }
    }

    @Test
    void shouldPlaceXAtSpecifiedPosition() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);

        assertEquals(Mark.X, board.getMarkAt(0, 0));
    }

    @Test
    void shouldPlaceOAtSpecifiedPosition() {
        Board board = new Board();

        board.placeMark(1, 1, Mark.O);

        assertEquals(Mark.O, board.getMarkAt(1, 1));
    }

    @Test
    void shouldKeepOtherCellsEmptyWhenMarkIsPlaced() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);

        assertTrue(board.isCellEmpty(0, 1));
        assertTrue(board.isCellEmpty(1, 0));
        assertTrue(board.isCellEmpty(2, 2));
    }

    @Test
    void shouldRejectNegativeRow() {
        Board board = new Board();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> board.placeMark(-1, 0, Mark.X)
        );
    }

    @Test
    void shouldRejectRowOutsideBoard() {
        Board board = new Board();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> board.placeMark(3, 0, Mark.X)
        );
    }

    @Test
    void shouldRejectNegativeColumn() {
        Board board = new Board();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> board.placeMark(0, -1, Mark.X)
        );
    }

    @Test
    void shouldRejectColumnOutsideBoard() {
        Board board = new Board();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> board.placeMark(0, 3, Mark.X)
        );
    }

    @Test
    void shouldNotAllowOverwritingOccupiedPosition() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);

        assertThrows(
                IllegalStateException.class,
                () -> board.placeMark(0, 0, Mark.O)
        );
    }

    @Test
    void shouldRejectNullMark() {
        Board board = new Board();

        assertThrows(
                IllegalArgumentException.class,
                () -> board.placeMark(0, 0, null)
        );
    }

    // Horizontal winning condition

    @Test
    void shouldDetectHorizontalWinnerInFirstRow() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(0, 1, Mark.X);
        board.placeMark(0, 2, Mark.X);

        assertTrue(board.hasHorizontalWinner(Mark.X));
    }

    @Test
    void shouldDetectHorizontalWinnerInMiddleRow() {
        Board board = new Board();

        board.placeMark(1, 0, Mark.O);
        board.placeMark(1, 1, Mark.O);
        board.placeMark(1, 2, Mark.O);

        assertTrue(board.hasHorizontalWinner(Mark.O));
    }

    @Test
    void shouldDetectHorizontalWinnerInLastRow() {
        Board board = new Board();

        board.placeMark(2, 0, Mark.X);
        board.placeMark(2, 1, Mark.X);
        board.placeMark(2, 2, Mark.X);

        assertTrue(board.hasHorizontalWinner(Mark.X));
    }

    @Test
    void shouldReturnFalseWhenNoHorizontalWinnerExists() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(0, 1, Mark.O);
        board.placeMark(0, 2, Mark.X);

        assertFalse(board.hasHorizontalWinner(Mark.X));
    }

    @Test
    void shouldReturnFalseForHorizontalWinnerOnEmptyBoard() {
        Board board = new Board();

        assertFalse(board.hasHorizontalWinner(Mark.X));
    }

    @Test
    void shouldRejectNullMarkWhenCheckingHorizontalWinner() {
        Board board = new Board();

        assertThrows(
                IllegalArgumentException.class,
                () -> board.hasHorizontalWinner(null)
        );
    }

    // Vertical winning condition

    @Test
    void shouldDetectVerticalWinnerInFirstColumn() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 0, Mark.X);
        board.placeMark(2, 0, Mark.X);

        assertTrue(board.hasVerticalWinner(Mark.X));
    }

    @Test
    void shouldDetectVerticalWinnerInMiddleColumn() {
        Board board = new Board();

        board.placeMark(0, 1, Mark.O);
        board.placeMark(1, 1, Mark.O);
        board.placeMark(2, 1, Mark.O);

        assertTrue(board.hasVerticalWinner(Mark.O));
    }

    @Test
    void shouldDetectVerticalWinnerInLastColumn() {
        Board board = new Board();

        board.placeMark(0, 2, Mark.X);
        board.placeMark(1, 2, Mark.X);
        board.placeMark(2, 2, Mark.X);

        assertTrue(board.hasVerticalWinner(Mark.X));
    }

    @Test
    void shouldReturnFalseWhenNoVerticalWinnerExists() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 0, Mark.O);
        board.placeMark(2, 0, Mark.X);

        assertFalse(board.hasVerticalWinner(Mark.X));
    }

    @Test
    void shouldReturnFalseForVerticalWinnerOnEmptyBoard() {
        Board board = new Board();

        assertFalse(board.hasVerticalWinner(Mark.X));
    }

    @Test
    void shouldRejectNullMarkWhenCheckingVerticalWinner() {
        Board board = new Board();

        assertThrows(
                IllegalArgumentException.class,
                () -> board.hasVerticalWinner(null)
        );
    }

    // Diagonal winning condition

    @Test
    void shouldDetectTopLeftToBottomRightDiagonalWinner() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 1, Mark.X);
        board.placeMark(2, 2, Mark.X);

        assertTrue(board.hasDiagonalWinner(Mark.X));
    }

    @Test
    void shouldDetectTopRightToBottomLeftDiagonalWinner() {
        Board board = new Board();

        board.placeMark(0, 2, Mark.O);
        board.placeMark(1, 1, Mark.O);
        board.placeMark(2, 0, Mark.O);

        assertTrue(board.hasDiagonalWinner(Mark.O));
    }

    @Test
    void shouldReturnFalseWhenNoDiagonalWinnerExists() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 1, Mark.O);
        board.placeMark(2, 2, Mark.X);

        assertFalse(board.hasDiagonalWinner(Mark.X));
    }

    @Test
    void shouldReturnFalseForDiagonalWinnerOnEmptyBoard() {
        Board board = new Board();

        assertFalse(board.hasDiagonalWinner(Mark.X));
    }

    @Test
    void shouldRejectNullMarkWhenCheckingDiagonalWinner() {
        Board board = new Board();

        assertThrows(
                IllegalArgumentException.class,
                () -> board.hasDiagonalWinner(null)
        );
    }

    // Overall winning condition

    @Test
    void shouldDetectWinnerWhenHorizontalCombinationExists() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(0, 1, Mark.X);
        board.placeMark(0, 2, Mark.X);

        assertTrue(board.hasWinner(Mark.X));
    }

    @Test
    void shouldDetectWinnerWhenVerticalCombinationExists() {
        Board board = new Board();

        board.placeMark(0, 1, Mark.O);
        board.placeMark(1, 1, Mark.O);
        board.placeMark(2, 1, Mark.O);

        assertTrue(board.hasWinner(Mark.O));
    }

    @Test
    void shouldDetectWinnerWhenDiagonalCombinationExists() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 1, Mark.X);
        board.placeMark(2, 2, Mark.X);

        assertTrue(board.hasWinner(Mark.X));
    }

    @Test
    void shouldReturnFalseWhenNoWinningCombinationExists() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(0, 1, Mark.O);
        board.placeMark(0, 2, Mark.X);
        board.placeMark(1, 0, Mark.O);
        board.placeMark(1, 1, Mark.X);
        board.placeMark(1, 2, Mark.O);

        assertFalse(board.hasWinner(Mark.X));
        assertFalse(board.hasWinner(Mark.O));
    }

    @Test
    void shouldRejectNullMarkWhenCheckingWinner() {
        Board board = new Board();

        assertThrows(
                IllegalArgumentException.class,
                () -> board.hasWinner(null)
        );
    }
}
