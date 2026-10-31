package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardAvailablePositionsTest {

    @Test
    void shouldReturnAllNinePositionsForEmptyBoard() {
        Board board = new Board();

        List<Position> positions = board.getAvailablePositions();

        assertEquals(9, positions.size());
    }

    @Test
    void shouldReturnOnlyEmptyPositions() {
        Board board = new Board();

        board.placeMark(0, 0, Mark.X);
        board.placeMark(1, 1, Mark.O);

        List<Position> positions = board.getAvailablePositions();

        assertEquals(7, positions.size());
        assertFalse(positions.contains(new Position(0, 0)));
        assertFalse(positions.contains(new Position(1, 1)));
        assertTrue(positions.contains(new Position(2, 2)));
    }

    @Test
    void shouldReturnNoPositionsWhenBoardIsFull() {
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

        assertTrue(board.getAvailablePositions().isEmpty());
    }

    @Test
    void shouldNotAllowModificationOfAvailablePositions() {
        Board board = new Board();

        List<Position> positions = board.getAvailablePositions();

        assertThrows(
                UnsupportedOperationException.class,
                () -> positions.add(new Position(0, 0))
        );
    }
}