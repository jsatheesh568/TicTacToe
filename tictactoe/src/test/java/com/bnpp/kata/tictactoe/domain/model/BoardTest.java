package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

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
}