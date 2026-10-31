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

        assertThat(game.getBoard().getCell(0, 0).getMark())
                .isEqualTo(Mark.X);

        assertThat(game.getCurrentMark()).isEqualTo(Mark.O);
    }

    @Test
    void shouldPlaceOAndSwitchTurnToX() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        assertThat(game.getBoard().getCell(0, 0).getMark())
                .isEqualTo(Mark.X);

        assertThat(game.getBoard().getCell(1, 1).getMark())
                .isEqualTo(Mark.O);

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