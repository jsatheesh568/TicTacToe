package com.bnpp.kata.tictactoe.api.dto;

import com.bnpp.kata.tictactoe.domain.model.GameStatus;
import com.bnpp.kata.tictactoe.domain.model.Mark;
import com.bnpp.kata.tictactoe.domain.model.PlayerType;

public class GameStartResponse {

    private final String gameId;

    private final String playerXName;
    private final Mark playerXMark;
    private final PlayerType playerXType;

    private final String playerOName;
    private final Mark playerOMark;
    private final PlayerType playerOType;

    private final String currentPlayerName;
    private final Mark currentMark;
    private final GameStatus status;

    public GameStartResponse(
            String gameId,
            String playerXName,
            Mark playerXMark,
            PlayerType playerXType,
            String playerOName,
            Mark playerOMark,
            PlayerType playerOType,
            String currentPlayerName,
            Mark currentMark,
            GameStatus status
    ) {
        this.gameId = gameId;
        this.playerXName = playerXName;
        this.playerXMark = playerXMark;
        this.playerXType = playerXType;
        this.playerOName = playerOName;
        this.playerOMark = playerOMark;
        this.playerOType = playerOType;
        this.currentPlayerName = currentPlayerName;
        this.currentMark = currentMark;
        this.status = status;
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