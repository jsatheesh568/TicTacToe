package com.bnpp.kata.tictactoe.domain.model;

import com.bnpp.kata.tictactoe.domain.service.ComputerPlayer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameComputerPlayerTest {

    @Test
    void shouldAllowComputerToPlayWhenItIsItsTurn() {
        Player human = new Player("Alice", Mark.X);

        Player computer = new Player(
                "Computer",
                Mark.O,
                PlayerType.COMPUTER
        );

        Game game = new Game(
                human,
                computer,
                new ComputerPlayer(new Random(1))
        );

        game.play(0, 0);
        game.playComputerMove();

        assertEquals(2, game.getMoveHistory().size());
        assertEquals(Mark.X, game.getCurrentMark());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    void shouldRejectComputerMoveWhenItIsHumanTurn() {
        Player human = new Player("Alice", Mark.X);

        Player computer = new Player(
                "Computer",
                Mark.O,
                PlayerType.COMPUTER
        );

        Game game = new Game(human, computer);

        assertThrows(
                IllegalStateException.class,
                game::playComputerMove
        );
    }

    @Test
    void shouldRecordComputerMoveInHistory() {
        Player human = new Player("Alice", Mark.X);

        Player computer = new Player(
                "Computer",
                Mark.O,
                PlayerType.COMPUTER
        );

        Game game = new Game(human, computer);

        game.play(0, 0);
        game.playComputerMove();

        Move computerMove = game.getMoveHistory().get(1);

        assertEquals(Mark.O, computerMove.getMark());
    }

    @Test
    void shouldAllowUndoOfComputerMove() {
        Player human = new Player("Alice", Mark.X);

        Player computer = new Player(
                "Computer",
                Mark.O,
                PlayerType.COMPUTER
        );

        Game game = new Game(human, computer);

        game.play(0, 0);
        game.playComputerMove();

        Move computerMove = game.getMoveHistory().get(1);

        game.undoLastMove();

        assertNull(
                game.getMarkAt(
                        computerMove.getRow(),
                        computerMove.getColumn()
                )
        );

        assertEquals(Mark.O, game.getCurrentMark());
        assertEquals(1, game.getMoveHistory().size());
    }

    @Test
    void shouldRejectComputerMoveAfterGameIsCompleted() {

        Player human = new Player("Alice", Mark.X);

        Player computer = new Player(
                "Computer",
                Mark.O,
                PlayerType.COMPUTER
        );

        ComputerPlayer predictableComputer = new ComputerPlayer(
                new Random(1)
        ) {
            @Override
            public Position choosePosition(
                    Player player,
                    List<Position> availablePositions
            ) {
                return availablePositions.get(0);
            }
        };

        Game game = new Game(
                human,
                computer,
                predictableComputer
        );

        game.play(0, 0); // X
        game.playComputerMove(); // O at (0, 1)
        game.play(1, 0); // X
        game.playComputerMove(); // O at (0, 2)
        game.play(2, 0); // X wins vertically

        assertEquals(GameStatus.WON, game.getStatus());

        assertThrows(
                IllegalStateException.class,
                game::playComputerMove
        );
    }
}