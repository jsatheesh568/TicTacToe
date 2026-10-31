package com.bnpp.kata.tictactoe.domain.service;

import com.bnpp.kata.tictactoe.domain.model.Mark;
import com.bnpp.kata.tictactoe.domain.model.Player;
import com.bnpp.kata.tictactoe.domain.model.PlayerType;
import com.bnpp.kata.tictactoe.domain.model.Position;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {

    @Test
    void shouldSelectAnAvailablePosition() {
        ComputerPlayer computerPlayer =
                new ComputerPlayer(new Random(1));

        Player computer = new Player(
                "Computer",
                Mark.O,
                PlayerType.COMPUTER
        );

        List<Position> availablePositions = Arrays.asList(
                new Position(0, 0),
                new Position(1, 1),
                new Position(2, 2)
        );

        Position selectedPosition = computerPlayer.choosePosition(
                computer,
                availablePositions
        );

        assertNotNull(selectedPosition);
        assertTrue(availablePositions.contains(selectedPosition));
    }

    @Test
    void shouldRejectHumanPlayer() {
        ComputerPlayer computerPlayer = new ComputerPlayer();

        Player human = new Player("Alice", Mark.X);

        assertThrows(
                IllegalArgumentException.class,
                () -> computerPlayer.choosePosition(
                        human,
                        Collections.singletonList(new Position(0, 0))
                )
        );
    }

    @Test
    void shouldRejectNullPlayer() {
        ComputerPlayer computerPlayer = new ComputerPlayer();

        assertThrows(
                IllegalArgumentException.class,
                () -> computerPlayer.choosePosition(
                        null,
                        Collections.singletonList(new Position(0, 0))
                )
        );
    }

    @Test
    void shouldRejectNullAvailablePositions() {
        ComputerPlayer computerPlayer = new ComputerPlayer();

        Player computer = new Player(
                "Computer",
                Mark.O,
                PlayerType.COMPUTER
        );

        assertThrows(
                IllegalStateException.class,
                () -> computerPlayer.choosePosition(computer, null)
        );
    }

    @Test
    void shouldRejectEmptyAvailablePositions() {
        ComputerPlayer computerPlayer = new ComputerPlayer();

        Player computer = new Player(
                "Computer",
                Mark.O,
                PlayerType.COMPUTER
        );

        assertThrows(
                IllegalStateException.class,
                () -> computerPlayer.choosePosition(
                        computer,
                        Collections.emptyList()
                )
        );
    }

    @Test
    void shouldRejectNullRandom() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ComputerPlayer(null)
        );
    }
}