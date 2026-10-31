
package com.bnpp.kata.tictactoe.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int BOARD_SIZE = 3;
    private static final int TOTAL_CELLS = BOARD_SIZE * BOARD_SIZE;

    private final List<Cell> cells;

    public Board() {
        this.cells = new ArrayList<>(TOTAL_CELLS);

        for (int index = 0; index < TOTAL_CELLS; index++) {
            cells.add(new Cell());
        }
    }

    public void placeMark(int row, int column, Mark mark) {
        getCell(row, column).placeMark(mark);
    }

    public Mark getMarkAt(int row, int column) {
        return getCell(row, column).getMark();
    }

    public boolean isCellEmpty(int row, int column) {
        return getCell(row, column).isEmpty();
    }

    public boolean isFull() {
        for (Cell cell : cells) {
            if (cell.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void removeMark(int row, int column) {
        getCell(row, column).clear();
    }

    public void reset() {
        for (int index = 0; index < TOTAL_CELLS; index++) {
            cells.set(index, new Cell());
        }
    }

    public boolean hasHorizontalWinner(Mark mark) {
        validateMark(mark);

        for (int row = 0; row < BOARD_SIZE; row++) {
            boolean winningRow = true;

            for (int column = 0; column < BOARD_SIZE; column++) {
                if (getMarkAt(row, column) != mark) {
                    winningRow = false;
                    break;
                }
            }

            if (winningRow) {
                return true;
            }
        }

        return false;
    }

    public boolean hasVerticalWinner(Mark mark) {
        validateMark(mark);

        for (int column = 0; column < BOARD_SIZE; column++) {
            boolean winningColumn = true;

            for (int row = 0; row < BOARD_SIZE; row++) {
                if (getMarkAt(row, column) != mark) {
                    winningColumn = false;
                    break;
                }
            }

            if (winningColumn) {
                return true;
            }
        }

        return false;
    }

    public boolean hasDiagonalWinner(Mark mark) {
        validateMark(mark);

        boolean topLeftToBottomRight = true;
        boolean topRightToBottomLeft = true;

        for (int index = 0; index < BOARD_SIZE; index++) {
            if (getMarkAt(index, index) != mark) {
                topLeftToBottomRight = false;
            }

            if (getMarkAt(index, BOARD_SIZE - 1 - index) != mark) {
                topRightToBottomLeft = false;
            }
        }

        return topLeftToBottomRight || topRightToBottomLeft;
    }

    public boolean hasWinner(Mark mark) {
        validateMark(mark);

        return hasHorizontalWinner(mark)
                || hasVerticalWinner(mark)
                || hasDiagonalWinner(mark);
    }

    private Cell getCell(int row, int column) {
        validatePosition(row, column);
        int index = calculateIndex(row, column);
        return cells.get(index);
    }

    private void validatePosition(int row, int column) {
        if (row < 0 || row >= BOARD_SIZE
                || column < 0 || column >= BOARD_SIZE) {
            throw new IndexOutOfBoundsException(
                    "Invalid board position: row=" + row
                            + ", column=" + column
            );
        }
    }

    private void validateMark(Mark mark) {
        if (mark == null) {
            throw new IllegalArgumentException(
                    "Player mark cannot be null"
            );
        }
    }

    private int calculateIndex(int row, int column) {
        return row * BOARD_SIZE + column;
    }
}
