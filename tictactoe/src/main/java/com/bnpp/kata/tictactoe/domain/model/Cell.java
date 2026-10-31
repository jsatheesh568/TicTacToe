package com.bnpp.kata.tictactoe.domain.model;

public class Cell {

    private Mark mark;

    public boolean isEmpty() {
        return mark == null;
    }

    public Mark getMark() {
        return mark;
    }

    public void placeMark(Mark mark) {

        if (mark == null) {
            throw new IllegalArgumentException(
                    "Player mark cannot be null"
            );
        }

        if (!isEmpty()) {
            throw new IllegalStateException(
                    "Cell is already occupied"
            );
        }

        this.mark = mark;
    }
}