package com.bnpp.kata.tictactoe.domain.model;

public final class Player {

    private final String name;
    private final Mark mark;

    public Player(String name, Mark mark) {
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

        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public Mark getMark() {
        return mark;
    }
}