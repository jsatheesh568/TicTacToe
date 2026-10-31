package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    void shouldInitializeAllNineCellsAsEmpty() {
        Board board = new Board();

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertThat(board.isCellEmpty(row, column)).isTrue();
            }
        }
    }

    @Test
    void shouldPlaceXAtSpecifiedPosition() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);

        assertThat(board.getMarkAt(0, 0)).isEqualTo(Mark.X);
    }

    @Test
    void shouldPlaceOAtSpecifiedPosition() {
        Board board = new Board();

        board.placeMark(2, 2, Mark.O);

        assertThat(board.getMarkAt(2, 2)).isEqualTo(Mark.O);
    }

    @Test
    void shouldKeepOtherCellsEmptyWhenMarkIsPlaced() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);

        assertThat(board.getMarkAt(0, 0)).isEqualTo(Mark.X);
        assertThat(board.isCellEmpty(0, 1)).isTrue();
        assertThat(board.isCellEmpty(1, 1)).isTrue();
        assertThat(board.isCellEmpty(2, 2)).isTrue();
    }

    @Test
    void shouldRejectNegativeRow() {
        Board board = new Board();

        assertThatThrownBy(() -> board.placeMark(-1, 0, Mark.X))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void shouldRejectRowOutsideBoard() {
        Board board = new Board();

        assertThatThrownBy(() -> board.placeMark(3, 0, Mark.X))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void shouldRejectNegativeColumn() {
        Board board = new Board();

        assertThatThrownBy(() -> board.placeMark(0, -1, Mark.X))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void shouldRejectColumnOutsideBoard() {
        Board board = new Board();

        assertThatThrownBy(() -> board.placeMark(0, 3, Mark.X))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void shouldNotAllowOverwritingOccupiedPosition() {
        Board board = new Board();
        board.placeMark(0, 0, Mark.X);

        assertThatThrownBy(() -> board.placeMark(0, 0, Mark.O))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldRejectNullMark() {
        Board board = new Board();

        assertThatThrownBy(() -> board.placeMark(0, 0, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // Horizontal winning tests

    @Test
    void shouldDetectHorizontalWinInFirstRow() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(0, 1, Mark.X);
        board.placeMark(0, 2, Mark.X);

        assertThat(board.hasHorizontalWinner(Mark.X)).isTrue();
    }

    @Test
    void shouldDetectHorizontalWinInMiddleRow() {
        Board board = new Board();

        board.placeMark(1, 0, Mark.O);
        board.placeMark(1, 1, Mark.O);
        board.placeMark(1, 2, Mark.O);

        assertThat(board.hasHorizontalWinner(Mark.O)).isTrue();
    }

    @Test
    void shouldDetectHorizontalWinInLastRow() {
        Board board = new Board();

        board.placeMark(2, 0, Mark.X);
        board.placeMark(2, 1, Mark.X);
        board.placeMark(2, 2, Mark.X);

        assertThat(board.hasHorizontalWinner(Mark.X)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNoHorizontalWinningRowExists() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(0, 1, Mark.X);
        board.placeMark(0, 2, Mark.O);

        assertThat(board.hasHorizontalWinner(Mark.X)).isFalse();
    }

    @Test
    void shouldReturnFalseWhenBoardIsEmpty() {
        Board board = new Board();

        assertThat(board.hasHorizontalWinner(Mark.X)).isFalse();
    }

    @Test
    void shouldRejectNullMarkWhenCheckingHorizontalWinner() {
        Board board = new Board();

        assertThatThrownBy(() -> board.hasHorizontalWinner(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // Vertical winning tests

    @Test
    void shouldDetectVerticalWinInFirstColumn() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 0, Mark.X);
        board.placeMark(2, 0, Mark.X);

        assertThat(board.hasVerticalWinner(Mark.X)).isTrue();
    }

    @Test
    void shouldDetectVerticalWinInMiddleColumn() {
        Board board = new Board();

        board.placeMark(0, 1, Mark.O);
        board.placeMark(1, 1, Mark.O);
        board.placeMark(2, 1, Mark.O);

        assertThat(board.hasVerticalWinner(Mark.O)).isTrue();
    }

    @Test
    void shouldDetectVerticalWinInLastColumn() {
        Board board = new Board();

        board.placeMark(0, 2, Mark.X);
        board.placeMark(1, 2, Mark.X);
        board.placeMark(2, 2, Mark.X);

        assertThat(board.hasVerticalWinner(Mark.X)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNoVerticalWinningColumnExists() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 0, Mark.X);
        board.placeMark(2, 0, Mark.O);

        assertThat(board.hasVerticalWinner(Mark.X)).isFalse();
    }

    @Test
    void shouldReturnFalseWhenBoardIsEmptyForVerticalCheck() {
        Board board = new Board();

        assertThat(board.hasVerticalWinner(Mark.X)).isFalse();
    }

    @Test
    void shouldRejectNullMarkWhenCheckingVerticalWinner() {
        Board board = new Board();

        assertThatThrownBy(() -> board.hasVerticalWinner(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // Diagonal winning tests

    @Test
    void shouldDetectDiagonalWinFromTopLeftToBottomRight() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 1, Mark.X);
        board.placeMark(2, 2, Mark.X);

        assertThat(board.hasDiagonalWinner(Mark.X)).isTrue();
    }

    @Test
    void shouldDetectDiagonalWinFromTopRightToBottomLeft() {
        Board board = new Board();

        board.placeMark(0, 2, Mark.O);
        board.placeMark(1, 1, Mark.O);
        board.placeMark(2, 0, Mark.O);

        assertThat(board.hasDiagonalWinner(Mark.O)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNoDiagonalWinningCombinationExists() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 1, Mark.X);
        board.placeMark(2, 2, Mark.O);

        assertThat(board.hasDiagonalWinner(Mark.X)).isFalse();
    }

    @Test
    void shouldReturnFalseWhenBoardIsEmptyForDiagonalCheck() {
        Board board = new Board();

        assertThat(board.hasDiagonalWinner(Mark.X)).isFalse();
    }

    @Test
    void shouldRejectNullMarkWhenCheckingDiagonalWinner() {
        Board board = new Board();

        assertThatThrownBy(() -> board.hasDiagonalWinner(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}