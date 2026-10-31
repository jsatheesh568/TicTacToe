package com.bnpp.kata.tictactoe.domain.model;

public class Game {

    private final Board board;
    private Mark currentMark;
    private GameStatus status;

    public Game() {
        this.board = new Board();
        this.currentMark = Mark.X;
        this.status = GameStatus.IN_PROGRESS;
    }

    public Mark getCurrentMark() {
        return currentMark;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Mark getMarkAt(int row, int column) {
        return board.getMarkAt(row, column);
    }

    public void play(int row, int column) {
        board.placeMark(row, column, currentMark);

        if (board.hasWinner(currentMark)) {
            status = GameStatus.WON;
            return;
        }

        switchTurn();
    }

    private void switchTurn() {
        currentMark = currentMark == Mark.X
                ? Mark.O
                : Mark.X;
    }
}