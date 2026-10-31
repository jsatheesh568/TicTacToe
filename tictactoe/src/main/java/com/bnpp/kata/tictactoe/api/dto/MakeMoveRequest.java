
package com.bnpp.kata.tictactoe.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MakeMoveRequest {

    @NotNull(message = "Row is required")
    @Min(value = 0, message = "Row must be between 0 and 2")
    @Max(value = 2, message = "Row must be between 0 and 2")
    private Integer row;

    @NotNull(message = "Column is required")
    @Min(value = 0, message = "Column must be between 0 and 2")
    @Max(value = 2, message = "Column must be between 0 and 2")
    private Integer column;

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
