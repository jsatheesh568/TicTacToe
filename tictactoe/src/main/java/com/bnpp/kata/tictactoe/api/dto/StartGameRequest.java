package com.bnpp.kata.tictactoe.api.dto;

import com.bnpp.kata.tictactoe.domain.model.PlayerType;

public class StartGameRequest {

    private String playerXName;
    private String playerOName;
    private PlayerType playerXType;
    private PlayerType playerOType;

    public String getPlayerXName() {
        return playerXName;
    }

    public void setPlayerXName(String playerXName) {
        this.playerXName = playerXName;
    }

    public String getPlayerOName() {
        return playerOName;
    }

    public void setPlayerOName(String playerOName) {
        this.playerOName = playerOName;
    }

    public PlayerType getPlayerXType() {
        return playerXType;
    }

    public void setPlayerXType(PlayerType playerXType) {
        this.playerXType = playerXType;
    }

    public PlayerType getPlayerOType() {
        return playerOType;
    }

    public void setPlayerOType(PlayerType playerOType) {
        this.playerOType = playerOType;
    }
}