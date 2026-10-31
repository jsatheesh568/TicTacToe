package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    void shouldInitializeBoardWithNineCells() {
        Board board = new Board();

        assertThat(board.getCells()).hasSize(9);
    }

    @Test
    void shouldInitializeAllCellsAsEmpty() {
        Board board = new Board();

        assertThat(board.getCells())
                .allMatch(Cell::isEmpty);
    }

    @Test
    void shouldPlaceXAtSpecifiedPosition() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);

        assertThat(board.getCell(0, 0).getMark())
                .isEqualTo(Mark.X);
    }

    @Test
    void shouldPlaceOAtSpecifiedPosition() {
        Board board = new Board();

        board.placeMark(2, 2, Mark.O);

        assertThat(board.getCell(2, 2).getMark())
                .isEqualTo(Mark.O);
    }

    @Test
    void shouldKeepOtherCellsEmptyWhenMarkIsPlaced() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);

        assertThat(board.getCell(0, 0).getMark())
                .isEqualTo(Mark.X);

        assertThat(board.getCell(0, 1).isEmpty()).isTrue();
        assertThat(board.getCell(1, 1).isEmpty()).isTrue();
        assertThat(board.getCell(2, 2).isEmpty()).isTrue();
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
}