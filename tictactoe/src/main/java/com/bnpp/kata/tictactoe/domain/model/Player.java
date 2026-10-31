package com.bnpp.kata.tictactoe.domain.model;

public final class Player {

    private final String name;
    private final Mark mark;
    private final PlayerType type;

    public Player(String name, Mark mark) {
        this(name, mark, PlayerType.HUMAN);
    }

    public Player(String name, Mark mark, PlayerType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Player name cannot be empty"
            );
        }

        if (mark == null) {
            throw new IllegalArgumentException(
                    "Player mark cannot be null"
            );
        }

        if (type == null) {
            throw new IllegalArgumentException(
                    "Player type cannot be null"
            );
        }

        this.name = name;
        this.mark = mark;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Mark getMark() {
        return mark;
    }

    public PlayerType getType() {
        return type;
    }

    public boolean isComputer() {
        return type == PlayerType.COMPUTER;
    }
}