package com.bnpp.kata.tictactoe.domain.service;

import com.bnpp.kata.tictactoe.domain.model.Player;
import com.bnpp.kata.tictactoe.domain.model.Position;

import java.util.List;
import java.util.Random;

public class ComputerPlayer {

    private final Random random;

    public ComputerPlayer() {
        this(new Random());
    }

    public ComputerPlayer(Random random) {
        if (random == null) {
            throw new IllegalArgumentException(
                    "Random cannot be null"
            );
        }

        this.random = random;
    }

    public Position choosePosition(
            Player player,
            List<Position> availablePositions
    ) {
        if (player == null || !player.isComputer()) {
            throw new IllegalArgumentException(
                    "A computer player is required"
            );
        }

        if (availablePositions == null || availablePositions.isEmpty()) {
            throw new IllegalStateException(
                    "No available positions"
            );
        }

        int selectedIndex = random.nextInt(availablePositions.size());

        return availablePositions.get(selectedIndex);
    }
}