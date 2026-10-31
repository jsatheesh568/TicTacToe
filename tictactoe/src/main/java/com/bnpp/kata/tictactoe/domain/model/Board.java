package com.bnpp.kata.tictactoe.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    private static final int TOTAL_CELLS = 9;

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
}

