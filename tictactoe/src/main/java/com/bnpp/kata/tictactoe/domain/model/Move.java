
package com.bnpp.kata.tictactoe.domain.model;

public final class Move {

    private final Mark mark;
    private final int row;
    private final int column;

    public Move(Mark mark, int row, int column) {
        if (mark == null) {
            throw new IllegalArgumentException(
                    "Player mark cannot be null"
            );
        }

        this.mark = mark;
        this.row = row;
        this.column = column;
    }

    public Mark getMark() {
        return mark;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
