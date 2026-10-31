package com.bnpp.kata.tictactoe.domain.model;

import com.bnpp.kata.tictactoe.domain.service.ComputerPlayer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Game {

    private final Board board;
    private final List<Move> moveHistory;
    private final Deque<Move> redoHistory;

    private final Player playerX;
    private final Player playerO;
    private final ComputerPlayer computerPlayer;

    private Player currentPlayer;
    private GameStatus status;

    public Game() {
        this(
                new Player("Player X", Mark.X),
                new Player("Player O", Mark.O)
        );
    }

    public Game(Player playerX, Player playerO) {
        this(playerX, playerO, new ComputerPlayer());
    }

    public Game(
            Player playerX,
            Player playerO,
            ComputerPlayer computerPlayer
    ) {
        validatePlayers(playerX, playerO);

        if (computerPlayer == null) {
            throw new IllegalArgumentException(
                    "Computer player service cannot be null"
            );
        }

        this.board = new Board();
        this.moveHistory = new ArrayList<>();
        this.redoHistory = new ArrayDeque<>();

        this.playerX = playerX;
        this.playerO = playerO;
        this.computerPlayer = computerPlayer;

        this.currentPlayer = playerX;
        this.status = GameStatus.IN_PROGRESS;
    }

    public Mark getCurrentMark() {
        return currentPlayer.getMark();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer(Mark mark) {
        if (mark == null) {
            throw new IllegalArgumentException(
                    "Player mark cannot be null"
            );
        }

        return mark == Mark.X ? playerX : playerO;
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

        Mark mark = currentPlayer.getMark();

        board.placeMark(row, column, mark);

        moveHistory.add(new Move(mark, row, column));

        redoHistory.clear();

        updateGameStatus();
    }

    public void playComputerMove() {
        validateGameIsInProgress();

        if (!currentPlayer.isComputer()) {
            throw new IllegalStateException(
                    "It is not the computer player's turn"
            );
        }

        Position position = computerPlayer.choosePosition(
                currentPlayer,
                board.getAvailablePositions()
        );

        play(position.getRow(), position.getColumn());
    }

    public void undoLastMove() {
        if (moveHistory.isEmpty()) {
            throw new IllegalStateException(
                    "No moves available to undo"
            );
        }

        Move lastMove = moveHistory.get(moveHistory.size() - 1);

        board.removeMark(lastMove.getRow(), lastMove.getColumn());

        moveHistory.remove(moveHistory.size() - 1);

        redoHistory.push(lastMove);

        currentPlayer = getPlayer(lastMove.getMark());
        status = GameStatus.IN_PROGRESS;
    }

    public void redoLastMove() {
        if (redoHistory.isEmpty()) {
            throw new IllegalStateException(
                    "No moves available to redo"
            );
        }

        Move move = redoHistory.pop();

        board.placeMark(
                move.getRow(),
                move.getColumn(),
                move.getMark()
        );

        moveHistory.add(move);

        currentPlayer = getPlayer(move.getMark());

        updateGameStatus();
    }

    public void restart() {
        board.reset();
        moveHistory.clear();
        redoHistory.clear();

        currentPlayer = playerX;
        status = GameStatus.IN_PROGRESS;
    }

    private void updateGameStatus() {
        Mark mark = currentPlayer.getMark();

        if (board.hasWinner(mark)) {
            status = GameStatus.WON;
            return;
        }

        if (board.isFull()) {
            status = GameStatus.DRAW;
            return;
        }

        status = GameStatus.IN_PROGRESS;
        switchTurn();
    }

    private void validateGameIsInProgress() {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Game has already finished with status: " + status
            );
        }
    }

    private void switchTurn() {
        currentPlayer = currentPlayer == playerX
                ? playerO
                : playerX;
    }

    private void validatePlayers(Player playerX, Player playerO) {
        if (playerX == null || playerO == null) {
            throw new IllegalArgumentException(
                    "Both players must be provided"
            );
        }

        if (playerX.getMark() != Mark.X
                || playerO.getMark() != Mark.O) {
            throw new IllegalArgumentException(
                    "Player X must have mark X and Player O must have mark O"
            );
        }
    }
}