
package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardDrawTest {

    @Test
    void shouldReturnFalseWhenBoardIsEmpty() {
        Board board = new Board();

        assertFalse(board.isFull());
    }

    @Test
    void shouldReturnFalseWhenSomeCellsAreEmpty() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(0, 1, Mark.O);
        board.placeMark(1, 1, Mark.X);

        assertFalse(board.isFull());
    }

    @Test
    void shouldReturnTrueWhenAllCellsAreOccupied() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(0, 1, Mark.O);
        board.placeMark(0, 2, Mark.X);
        board.placeMark(1, 0, Mark.O);
        board.placeMark(1, 1, Mark.X);
        board.placeMark(1, 2, Mark.O);
        board.placeMark(2, 0, Mark.X);
        board.placeMark(2, 1, Mark.O);
        board.placeMark(2, 2, Mark.X);

        assertTrue(board.isFull());
    }
}
