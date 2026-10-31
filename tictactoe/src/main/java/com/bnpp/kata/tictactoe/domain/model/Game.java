
package com.bnpp.kata.tictactoe.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private final Board board;
    private final List<Move> moveHistory;

    private Mark currentMark;
    private GameStatus status;

    public Game() {
        this.board = new Board();
        this.moveHistory = new ArrayList<>();
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

    public List<Move> getMoveHistory() {
        return Collections.unmodifiableList(moveHistory);
    }

    public void play(int row, int column) {
        validateGameIsInProgress();

        board.placeMark(row, column, currentMark);

        moveHistory.add(new Move(currentMark, row, column));

        if (board.hasWinner(currentMark)) {
            status = GameStatus.WON;
            return;
        }

        if (board.isFull()) {
            status = GameStatus.DRAW;
            return;
        }

        switchTurn();
    }

    public void restart() {
        board.reset();
        moveHistory.clear();
        currentMark = Mark.X;
        status = GameStatus.IN_PROGRESS;
    }

    private void validateGameIsInProgress() {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Game has already finished with status: " + status
            );
        }
    }

    private void switchTurn() {
        currentMark = currentMark == Mark.X
                ? Mark.O
                : Mark.X;
    }
}
