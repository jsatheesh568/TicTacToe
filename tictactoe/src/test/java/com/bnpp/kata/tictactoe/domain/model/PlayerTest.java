package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void shouldCreatePlayerWithNameAndMark() {
        Player player = new Player("Satheesh", Mark.X);

        assertEquals("Satheesh", player.getName());
        assertEquals(Mark.X, player.getMark());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Player(null, Mark.X)
        );
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("", Mark.X)
        );
    }

    @Test
    void shouldThrowExceptionWhenNameContainsOnlyWhitespace() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("   ", Mark.X)
        );
    }

    @Test
    void shouldThrowExceptionWhenMarkIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("Satheesh", null)
        );
    }
}