package com.bnpp.kata.tictactoe.api.dto;

import com.bnpp.kata.tictactoe.domain.model.GameStatus;
import com.bnpp.kata.tictactoe.domain.model.Mark;
import com.bnpp.kata.tictactoe.domain.model.PlayerType;

import java.util.List;

public class GameStateResponse {

    private final String gameId;

    private final String playerXName;
    private final Mark playerXMark;
    private final PlayerType playerXType;

    private final String playerOName;
    private final Mark playerOMark;
    private final PlayerType playerOType;

    private final List<List<String>> board;

    private final String currentPlayerName;
    private final Mark currentMark;
    private final GameStatus status;

    private final List<MoveHistoryResponse> moveHistory;

    public GameStateResponse(
            String gameId,
            String playerXName,
            Mark playerXMark,
            PlayerType playerXType,
            String playerOName,
            Mark playerOMark,
            PlayerType playerOType,
            List<List<String>> board,
            String currentPlayerName,
            Mark currentMark,
            GameStatus status,
            List<MoveHistoryResponse> moveHistory
    ) {
        this.gameId = gameId;
        this.playerXName = playerXName;
        this.playerXMark = playerXMark;
        this.playerXType = playerXType;
        this.playerOName = playerOName;
        this.playerOMark = playerOMark;
        this.playerOType = playerOType;
        this.board = board;
        this.currentPlayerName = currentPlayerName;
        this.currentMark = currentMark;
        this.status = status;
        this.moveHistory = moveHistory;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerXName() {
        return playerXName;
    }

    public Mark getPlayerXMark() {
        return playerXMark;
    }

    public PlayerType getPlayerXType() {
        return playerXType;
    }

    public String getPlayerOName() {
        return playerOName;
    }

    public Mark getPlayerOMark() {
        return playerOMark;
    }

    public PlayerType getPlayerOType() {
        return playerOType;
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

    public List<MoveHistoryResponse> getMoveHistory() {
        return moveHistory;
    }
}