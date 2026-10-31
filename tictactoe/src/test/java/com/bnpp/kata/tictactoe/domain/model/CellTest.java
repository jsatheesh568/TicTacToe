package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CellTest {

    @Test
    void shouldInitializeCellAsEmpty() {
        Cell cell = new Cell();

        assertThat(cell.isEmpty()).isTrue();
        assertThat(cell.getMark()).isNull();
    }

    @Test
    void shouldAllowPlayerXToMarkCell() {
        Cell cell = new Cell();

        cell.placeMark(Mark.X);

        assertThat(cell.isEmpty()).isFalse();
        assertThat(cell.getMark()).isEqualTo(Mark.X);
    }

    @Test
    void shouldAllowPlayerOToMarkCell() {
        Cell cell = new Cell();

        cell.placeMark(Mark.O);

        assertThat(cell.isEmpty()).isFalse();
        assertThat(cell.getMark()).isEqualTo(Mark.O);
    }

    @Test
    void shouldNotAllowOverwritingExistingMark() {
        Cell cell = new Cell();
        cell.placeMark(Mark.X);

        assertThatThrownBy(() -> cell.placeMark(Mark.O))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldRejectNullMark() {
        Cell cell = new Cell();

        assertThatThrownBy(() -> cell.placeMark(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
