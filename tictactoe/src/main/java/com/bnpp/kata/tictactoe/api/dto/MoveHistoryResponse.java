package com.bnpp.kata.tictactoe.api.dto;

import com.bnpp.kata.tictactoe.domain.model.Mark;

public class MoveHistoryResponse {

    private final Mark mark;
    private final int row;
    private final int column;

    public MoveHistoryResponse(Mark mark, int row, int column) {
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