
package com.bnpp.kata.tictactoe.api.dto;

import com.bnpp.kata.tictactoe.domain.model.PlayerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StartGameRequest {

    @NotBlank(message = "Player X name is required")
    private String playerXName;

    @NotNull(message = "Player X type is required")
    private PlayerType playerXType;

    @NotBlank(message = "Player O name is required")
    private String playerOName;

    @NotNull(message = "Player O type is required")
    private PlayerType playerOType;

    public String getPlayerXName() {
        return playerXName;
    }

    public void setPlayerXName(String playerXName) {
        this.playerXName = playerXName;
    }

    public PlayerType getPlayerXType() {
        return playerXType;
    }

    public void setPlayerXType(PlayerType playerXType) {
        this.playerXType = playerXType;
    }

    public String getPlayerOName() {
        return playerOName;
    }

    public void setPlayerOName(String playerOName) {
        this.playerOName = playerOName;
    }

    public PlayerType getPlayerOType() {
        return playerOType;
    }

    public void setPlayerOType(PlayerType playerOType) {
        this.playerOType = playerOType;
    }
}
