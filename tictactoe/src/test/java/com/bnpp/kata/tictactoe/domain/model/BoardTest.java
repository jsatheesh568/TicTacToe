package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void shouldInitializeBoardWithNineCells() {
        Board board = new Board();
        int actualCellCount = board.getCells().size();
        assertThat(actualCellCount).isEqualTo(9);
    }

    @Test
    void shouldInitializeAllCellsAsEmpty() {
        Board board = new Board();
        boolean allCellsEmpty = board.getCells()
                .stream()
                .allMatch(Cell::isEmpty);
        assertThat(allCellsEmpty).isTrue();
    }
}
