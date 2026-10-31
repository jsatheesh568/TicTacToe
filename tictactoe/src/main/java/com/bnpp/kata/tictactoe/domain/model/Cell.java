package com.bnpp.kata.tictactoe.domain.model;

public class Cell {
    private boolean occupied;

    public boolean isEmpty() {
        return !occupied;
    }
}
