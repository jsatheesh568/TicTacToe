package com.bnpp.kata.tictactoe.domain.model;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<Cell> getCells() {
        return Collections.unmodifiableList(cells);
    }

    public Cell getCell(int row, int column) {
        validatePosition(row, column);

        int index = calculateIndex(row, column);

        return cells.get(index);
    }

    public void placeMark(int row, int column, Mark mark) {
        Cell cell = getCell(row, column);
        cell.placeMark(mark);
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

    private int calculateIndex(int row, int column) {
        return row * BOARD_SIZE + column;
    }
}