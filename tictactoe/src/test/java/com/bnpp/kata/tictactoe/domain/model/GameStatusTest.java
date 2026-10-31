
package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStatusTest {

    @Test
    void shouldDefineInProgressStatus() {
        assertEquals(
                "IN_PROGRESS",
                GameStatus.IN_PROGRESS.name()
        );
    }

    @Test
    void shouldDefineWonStatus() {
        assertEquals(
                "WON",
                GameStatus.WON.name()
        );
    }

    @Test
    void shouldDefineDrawStatus() {
        assertEquals(
                "DRAW",
                GameStatus.DRAW.name()
        );
    }

    @Test
    void shouldDefineExactlyThreeGameStatuses() {
        assertEquals(3, GameStatus.values().length);
    }
}
