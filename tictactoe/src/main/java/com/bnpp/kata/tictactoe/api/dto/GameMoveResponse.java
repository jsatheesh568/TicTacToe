package com.bnpp.kata.tictactoe.api.dto;

import com.bnpp.kata.tictactoe.domain.model.GameStatus;
import com.bnpp.kata.tictactoe.domain.model.Mark;

import java.util.List;

public class GameMoveResponse {

    private final String gameId;
    private final List<List<String>> board;
    private final String currentPlayerName;
    private final Mark currentMark;
    private final GameStatus status;

    public GameMoveResponse(
            String gameId,
            List<List<String>> board,
            String currentPlayerName,
            Mark currentMark,
            GameStatus status
    ) {
        this.gameId = gameId;
        this.board = board;
        this.currentPlayerName = currentPlayerName;
        this.currentMark = currentMark;
        this.status = status;
    }

    public String getGameId() {
        return gameId;
    }

    public List<List<String>> getBoard() {
        return board;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public Mark getCurrentMark() {
        return currentMark;
    }

    public GameStatus getStatus() {
        return status;
    }
}