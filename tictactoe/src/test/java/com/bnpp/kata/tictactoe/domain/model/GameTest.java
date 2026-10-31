
package com.bnpp.kata.tictactoe.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {

    @Test
    void shouldStartGameWithPlayerX() {
        Game game = new Game();

        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldStartGameWithInProgressStatus() {
        Game game = new Game();

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldStartWithEmptyMoveHistory() {
        Game game = new Game();

        assertEquals(0, game.getMoveHistory().size());
    }

    @Test
    void shouldPlaceXAndSwitchTurnToO() {
        Game game = new Game();

        game.play(0, 0);

        assertEquals(Mark.X, game.getMarkAt(0, 0));
        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldPlaceOAndSwitchTurnToX() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        assertEquals(Mark.O, game.getMarkAt(1, 1));
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldSetGameStatusToWonWhenPlayerWinsHorizontally() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldSetGameStatusToWonWhenPlayerWinsVertically() {
        Game game = new Game();

        game.play(0, 0);
        game.play(0, 1);
        game.play(1, 0);
        game.play(1, 1);
        game.play(2, 0);

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldSetGameStatusToWonWhenPlayerWinsDiagonally() {
        Game game = new Game();

        game.play(0, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);
        game.play(2, 2);

        assertEquals(GameStatus.WON, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldKeepGameInProgressWhenNoPlayerHasWon() {
        Game game = new Game();

        game.play(0, 0);
        game.play(0, 1);
        game.play(0, 2);
        game.play(1, 1);

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldSetGameStatusToDrawWhenBoardIsFullWithoutWinner() {
        Game game = new Game();

        playDrawSequence(game);

        assertEquals(GameStatus.DRAW, game.getStatus());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldRecordFirstMoveMadeByPlayerX() {
        Game game = new Game();

        game.play(0, 0);

        assertEquals(1, game.getMoveHistory().size());

        Move move = game.getMoveHistory().get(0);

        assertEquals(Mark.X, move.getMark());
        assertEquals(0, move.getRow());
        assertEquals(0, move.getColumn());
    }

    @Test
    void shouldRecordMovesMadeByBothPlayers() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

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
    void shouldMaintainMoveHistoryInChronologicalOrder() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);
        game.play(0, 1);

        List<Move> history = game.getMoveHistory();

        assertEquals(3, history.size());

        assertEquals(Mark.X, history.get(0).getMark());
        assertEquals(Mark.O, history.get(1).getMark());
        assertEquals(Mark.X, history.get(2).getMark());

        assertEquals(0, history.get(0).getRow());
        assertEquals(1, history.get(1).getRow());
        assertEquals(0, history.get(2).getRow());
    }

    @Test
    void shouldNotAllowExternalModificationOfMoveHistory() {
        Game game = new Game();

        game.play(0, 0);

        List<Move> history = game.getMoveHistory();

        assertThrows(
                UnsupportedOperationException.class,
                () -> history.add(new Move(Mark.O, 1, 1))
        );

        assertEquals(1, game.getMoveHistory().size());
    }

    @Test
    void shouldNotRecordMoveWhenPositionIsAlreadyOccupied() {
        Game game = new Game();

        game.play(0, 0);

        assertThrows(
                IllegalStateException.class,
                () -> game.play(0, 0)
        );

        assertEquals(1, game.getMoveHistory().size());
        assertEquals(Mark.O, game.getCurrentMark());
    }

    @Test
    void shouldNotRecordMoveWhenPositionIsInvalid() {
        Game game = new Game();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> game.play(3, 0)
        );

        assertEquals(0, game.getMoveHistory().size());
        assertEquals(Mark.X, game.getCurrentMark());
    }

    @Test
    void shouldNotRecordMoveWhenGameIsAlreadyWon() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);

        assertEquals(5, game.getMoveHistory().size());

        assertThrows(
                IllegalStateException.class,
                () -> game.play(2, 2)
        );

        assertEquals(5, game.getMoveHistory().size());
        assertNull(game.getMarkAt(2, 2));
    }

    @Test
    void shouldNotRecordMoveWhenGameIsAlreadyDrawn() {
        Game game = new Game();

        playDrawSequence(game);

        assertEquals(9, game.getMoveHistory().size());

        assertThrows(
                IllegalStateException.class,
                () -> game.play(0, 0)
        );

        assertEquals(9, game.getMoveHistory().size());
    }

    @Test
    void shouldClearMoveHistoryWhenGameIsRestarted() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);
        game.play(0, 1);

        assertEquals(3, game.getMoveHistory().size());

        game.restart();

        assertEquals(0, game.getMoveHistory().size());
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertNull(game.getMarkAt(row, column));
            }
        }
    }

    @Test
    void shouldRecordNewMovesAfterGameRestart() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        game.restart();

        game.play(2, 2);

        List<Move> history = game.getMoveHistory();

        assertEquals(1, history.size());
        assertEquals(Mark.X, history.get(0).getMark());
        assertEquals(2, history.get(0).getRow());
        assertEquals(2, history.get(0).getColumn());
    }

    @Test
    void shouldRestartGameAfterPlayerWins() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);

        assertEquals(GameStatus.WON, game.getStatus());

        game.restart();

        assertGameIsInInitialState(game);
    }

    @Test
    void shouldRestartGameAfterDraw() {
        Game game = new Game();

        playDrawSequence(game);

        assertEquals(GameStatus.DRAW, game.getStatus());

        game.restart();

        assertGameIsInInitialState(game);
    }

    @Test
    void shouldRestartGameWhileGameIsInProgress() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 1);

        game.restart();

        assertGameIsInInitialState(game);
    }

    @Test
    void shouldAllowPlayingAgainAfterRestart() {
        Game game = new Game();

        game.play(0, 0);
        game.play(1, 0);
        game.play(0, 1);
        game.play(1, 1);
        game.play(0, 2);

        game.restart();

        game.play(2, 2);

        assertEquals(Mark.X, game.getMarkAt(2, 2));
        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldNotChangeTurnWhenPositionIsAlreadyOccupied() {
        Game game = new Game();

        game.play(0, 0);

        assertThrows(
                IllegalStateException.class,
                () -> game.play(0, 0)
        );

        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldNotChangeTurnWhenPositionIsInvalid() {
        Game game = new Game();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> game.play(3, 0)
        );

        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
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

    private void assertGameIsInInitialState(Game game) {
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(0, game.getMoveHistory().size());

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertNull(game.getMarkAt(row, column));
            }
        }
    }
}
