package com.bnpp.kata.tictactoe.domain.model;

public class Game {

    private final Board board;
    private Mark currentMark;

    public Game() {
        this.board = new Board();
        this.currentMark = Mark.X;
    }

    public Mark getCurrentMark() {
        return currentMark;
    }

    public Mark getMarkAt(int row, int column) {
        return board.getMarkAt(row, column);
    }

    public void play(int row, int column) {
        board.placeMark(row, column, currentMark);

        switchTurn();
    }

    private void switchTurn() {
        currentMark = currentMark == Mark.X
                ? Mark.O
                : Mark.X;
    }
}