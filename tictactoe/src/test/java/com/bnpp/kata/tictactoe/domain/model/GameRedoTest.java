package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameRedoTest {

    @Test
    void shouldRedoLastUndoneMove() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(1, 1); // O

        game.undoLastMove();

        assertNull(game.getMarkAt(1, 1));
        assertEquals(Mark.O, game.getCurrentMark());

        game.redoLastMove();

        assertEquals(Mark.O, game.getMarkAt(1, 1));
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(2, game.getMoveHistory().size());
    }

    @Test
    void shouldRedoMultipleMovesInOriginalOrder() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(1, 1); // O
        game.play(0, 1); // X

        game.undoLastMove();
        game.undoLastMove();

        game.redoLastMove(); // O at (1,1)
        game.redoLastMove(); // X at (0,1)

        assertEquals(Mark.X, game.getMarkAt(0, 0));
        assertEquals(Mark.O, game.getMarkAt(1, 1));
        assertEquals(Mark.X, game.getMarkAt(0, 1));

        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(3, game.getMoveHistory().size());
    }

    @Test
    void shouldThrowExceptionWhenNoMovesAvailableToRedo() {
        Game game = new Game();

        assertThrows(
                IllegalStateException.class,
                game::redoLastMove
        );
    }

    @Test
    void shouldClearRedoHistoryWhenNewMoveIsPlayed() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(1, 1); // O

        game.undoLastMove();

        game.play(2, 2); // O - new move

        assertEquals(Mark.O, game.getMarkAt(2, 2));

        assertThrows(
                IllegalStateException.class,
                game::redoLastMove
        );
    }

    @Test
    void shouldClearRedoHistoryWhenGameIsRestarted() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        game.undoLastMove();
        game.restart();

        assertThrows(
                IllegalStateException.class,
                game::redoLastMove
        );

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
        assertTrue(game.getMoveHistory().isEmpty());
    }

    @Test
    void shouldRestoreWonStatusWhenWinningMoveIsRedone() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(1, 0); // O
        game.play(0, 1); // X
        game.play(1, 1); // O
        game.play(0, 2); // X wins

        game.undoLastMove();

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());

        game.redoLastMove();

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(Mark.X, game.getMarkAt(0, 2));
    }

    @Test
    void shouldRestoreDrawStatusWhenLastMoveIsRedone() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(0, 1); // O
        game.play(0, 2); // X
        game.play(1, 1); // O
        game.play(1, 0); // X
        game.play(1, 2); // O
        game.play(2, 1); // X
        game.play(2, 0); // O
        game.play(2, 2); // X - draw

        assertEquals(GameStatus.DRAW, game.getStatus());

        game.undoLastMove();

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());

        game.redoLastMove();

        assertEquals(GameStatus.DRAW, game.getStatus());
    }
}