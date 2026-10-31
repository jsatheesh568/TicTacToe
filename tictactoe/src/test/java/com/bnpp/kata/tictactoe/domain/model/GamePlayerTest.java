package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamePlayerTest {

    @Test
    void shouldStartGameWithPlayerX() {
        Player playerX = new Player("Alice", Mark.X);
        Player playerO = new Player("Bob", Mark.O);

        Game game = new Game(playerX, playerO);

        assertEquals(playerX, game.getCurrentPlayer());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldSwitchPlayersAfterSuccessfulMove() {
        Player playerX = new Player("Alice", Mark.X);
        Player playerO = new Player("Bob", Mark.O);

        Game game = new Game(playerX, playerO);

        game.play(0, 0);

        assertEquals(playerO, game.getCurrentPlayer());
        assertEquals(Mark.O, game.getCurrentMark());
    }

    @Test
    void shouldReturnPlayerByMark() {
        Player playerX = new Player("Alice", Mark.X);
        Player playerO = new Player("Bob", Mark.O);

        Game game = new Game(playerX, playerO);

        assertEquals(playerX, game.getPlayer(Mark.X));
        assertEquals(playerO, game.getPlayer(Mark.O));
    }

    @Test
    void shouldThrowExceptionWhenPlayerIsNull() {
        Player playerO = new Player("Bob", Mark.O);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(null, playerO)
        );

        Player playerX = new Player("Alice", Mark.X);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(playerX, null)
        );
    }

    @Test
    void shouldRejectPlayersWithIncorrectMarks() {
        Player playerX = new Player("Alice", Mark.O);
        Player playerO = new Player("Bob", Mark.X);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(playerX, playerO)
        );
    }

    @Test
    void shouldRestoreCorrectPlayerAfterUndo() {
        Player playerX = new Player("Alice", Mark.X);
        Player playerO = new Player("Bob", Mark.O);

        Game game = new Game(playerX, playerO);

        game.play(0, 0); // Alice
        game.play(1, 1); // Bob

        game.undoLastMove();

        assertEquals(playerO, game.getCurrentPlayer());
        assertEquals(Mark.O, game.getCurrentMark());
    }

    @Test
    void shouldRestoreCorrectPlayerAfterRedo() {
        Player playerX = new Player("Alice", Mark.X);
        Player playerO = new Player("Bob", Mark.O);

        Game game = new Game(playerX, playerO);

        game.play(0, 0); // Alice
        game.play(1, 1); // Bob

        game.undoLastMove();
        game.redoLastMove();

        assertEquals(playerX, game.getCurrentPlayer());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldRestorePlayerXAfterRestart() {
        Player playerX = new Player("Alice", Mark.X);
        Player playerO = new Player("Bob", Mark.O);

        Game game = new Game(playerX, playerO);

        game.play(0, 0);
        game.play(1, 1);

        game.restart();

        assertEquals(playerX, game.getCurrentPlayer());
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldRejectNullMarkWhenRetrievingPlayer() {
        Player playerX = new Player("Alice", Mark.X);
        Player playerO = new Player("Bob", Mark.O);

        Game game = new Game(playerX, playerO);

        assertThrows(
                IllegalArgumentException.class,
                () -> game.getPlayer(null)
        );
    }
}