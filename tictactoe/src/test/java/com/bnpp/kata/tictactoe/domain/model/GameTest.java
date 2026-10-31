package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameTest {

    @Test
    void shouldStartGameWithPlayerX() {
        Game game = new Game();

        assertThat(game.getCurrentMark()).isEqualTo(Mark.X);
    }

    @Test
    void shouldPlaceXAndSwitchTurnToO() {
        Game game = new Game();

        game.play(0, 0);

        assertThat(game.getMarkAt(0, 0)).isEqualTo(Mark.X);
        assertThat(game.getCurrentMark()).isEqualTo(Mark.O);
    }

    @Test
    void shouldPlaceOAndSwitchTurnToX() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        assertThat(game.getMarkAt(0, 0)).isEqualTo(Mark.X);
        assertThat(game.getMarkAt(1, 1)).isEqualTo(Mark.O);
        assertThat(game.getCurrentMark()).isEqualTo(Mark.X);
    }

    @Test
    void shouldNotChangeTurnWhenPositionIsAlreadyOccupied() {
        Game game = new Game();

        game.play(0, 0);

        assertThatThrownBy(() -> game.play(0, 0))
                .isInstanceOf(IllegalStateException.class);

        assertThat(game.getCurrentMark()).isEqualTo(Mark.O);
    }

    @Test
    void shouldNotChangeTurnWhenPositionIsInvalid() {
        Game game = new Game();

        assertThatThrownBy(() -> game.play(3, 0))
                .isInstanceOf(IndexOutOfBoundsException.class);

        assertThat(game.getCurrentMark()).isEqualTo(Mark.X);
    }
}