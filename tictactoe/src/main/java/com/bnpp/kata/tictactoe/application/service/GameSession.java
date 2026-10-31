package com.bnpp.kata.tictactoe.application.service;

import com.bnpp.kata.tictactoe.domain.model.Game;

public final class GameSession {

    private final String gameId;
    private final Game game;

    public GameSession(String gameId, Game game) {
        if (gameId == null || gameId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Game ID cannot be empty"
            );
        }

        if (game == null) {
            throw new IllegalArgumentException(
                    "Game cannot be null"
            );
        }

        this.gameId = gameId;
        this.game = game;
    }

    public String getGameId() {
        return gameId;
    }

    public Game getGame() {
        return game;
    }
}