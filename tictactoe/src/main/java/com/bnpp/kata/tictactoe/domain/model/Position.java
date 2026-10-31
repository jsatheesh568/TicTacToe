package com.bnpp.kata.tictactoe.domain.model;

import java.util.Objects;

public final class Position {

    private static final int BOARD_SIZE = 3;

    private final int row;
    private final int column;

    public Position(int row, int column) {
        if (row < 0 || row >= BOARD_SIZE
                || column < 0 || column >= BOARD_SIZE) {
            throw new IndexOutOfBoundsException(
                    "Invalid board position: row=" + row
                            + ", column=" + column
            );
        }

        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Position)) {
            return false;
        }

        Position position = (Position) object;

        return row == position.row
                && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}