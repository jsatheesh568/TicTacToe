
package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameUndoTest {

    @Test
    void shouldRejectUndoWhenNoMovesHaveBeenPlayed() {
        Game game = new Game();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                game::undoLastMove
        );

        assertEquals(
                "No moves available to undo",
                exception.getMessage()
        );

        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(0, game.getMoveHistory().size());
    }

    @Test
    void shouldUndoFirstMoveAndRestorePlayerX() {
        Game game = new Game();

        game.play(0, 0);

        game.undoLastMove();

        assertNull(game.getMarkAt(0, 0));
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(0, game.getMoveHistory().size());
    }

    @Test
    void shouldUndoLastMoveAndRestoreThePreviousPlayer() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        game.undoLastMove();

        assertEquals(Mark.X, game.getMarkAt(0, 0));
        assertNull(game.getMarkAt(1, 1));
        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(1, game.getMoveHistory().size());
    }

    @Test
    void shouldRemoveOnlyTheLastMoveFromHistory() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);
        game.play(0, 1);

        game.undoLastMove();

        List<Move> history = game.getMoveHistory();

        assertEquals(2, history.size());

        assertEquals(Mark.X, history.get(0).getMark());
        assertEquals(0, history.get(0).getRow());
        assertEquals(0, history.get(0).getColumn());

        assertEquals(Mark.O, history.get(1).getMark());
        assertEquals(1, history.get(1).getRow());
        assertEquals(1, history.get(1).getColumn());
    }

    @Test
    void shouldUndoWinningMoveAndReopenGame() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(5, game.getMoveHistory().size());

        game.undoLastMove();

        assertNull(game.getMarkAt(0, 2));
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(4, game.getMoveHistory().size());
    }

    @Test
    void shouldAllowPlayingAgainAfterUndoingWinningMove() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);

        game.undoLastMove();

        game.play(2, 2);

        assertEquals(Mark.X, game.getMarkAt(2, 2));
        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(5, game.getMoveHistory().size());
    }

    @Test
    void shouldUndoLastMoveAfterDrawAndReopenGame() {
        Game game = new Game();

        playDrawSequence(game);

        assertEquals(GameStatus.DRAW, game.getStatus());
        assertEquals(9, game.getMoveHistory().size());

        game.undoLastMove();

        assertNull(game.getMarkAt(2, 1));
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(8, game.getMoveHistory().size());
    }

    @Test
    void shouldAllowPlayingAgainAfterUndoingDraw() {
        Game game = new Game();

        playDrawSequence(game);

        game.undoLastMove();

        game.play(2, 1);

        assertEquals(Mark.X, game.getMarkAt(2, 1));
        assertEquals(GameStatus.DRAW, game.getStatus());
        assertEquals(9, game.getMoveHistory().size());
    }


    @Test
    void shouldUndoMultipleMovesInReverseOrder() {
        Game game = new Game();

        game.play(0, 0); // X
        game.play(1, 1); // O
        game.play(0, 1); // X

        game.undoLastMove(); // Undo X at (0,1)
        game.undoLastMove(); // Undo O at (1,1)

        assertEquals(Mark.X, game.getMarkAt(0, 0));
        assertNull(game.getMarkAt(0, 1));
        assertNull(game.getMarkAt(1, 1));

        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(1, game.getMoveHistory().size());
    }


    @Test
    void shouldAllowUndoAfterRestartOnlyForNewMoves() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        game.restart();

        game.play(2, 2);

        game.undoLastMove();

        assertNull(game.getMarkAt(2, 2));
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(0, game.getMoveHistory().size());
    }

    private void playDrawSequence(Game game) {
        game.play(0, 0);
        game.play(1, 1);
        game.play(0, 2);
        game.play(0, 1);
        game.play(1, 0);
        game.play(2, 0);
        game.play(1, 2);
        game.play(2, 2);
        game.play(2, 1);
    }
}
