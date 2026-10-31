package com.bnpp.kata.tictactoe.application.service;

import com.bnpp.kata.tictactoe.domain.model.Game;
import com.bnpp.kata.tictactoe.domain.model.Mark;
import com.bnpp.kata.tictactoe.domain.model.Player;
import com.bnpp.kata.tictactoe.domain.model.PlayerType;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class GameApplicationService {

    private final ConcurrentMap<String, Game> games =
            new ConcurrentHashMap<>();

    public String startGame(
            String playerXName,
            PlayerType playerXType,
            String playerOName,
            PlayerType playerOType
    ) {
        Player playerX = new Player(
                playerXName,
                Mark.X,
                resolvePlayerType(playerXType)
        );

        Player playerO = new Player(
                playerOName,
                Mark.O,
                resolvePlayerType(playerOType)
        );

        Game game = new Game(playerX, playerO);

        String gameId = UUID.randomUUID().toString();

        games.put(gameId, game);

        return gameId;
    }

    public Game getGame(String gameId) {
        Game game = games.get(gameId);

        if (game == null) {
            throw new IllegalArgumentException(
                    "Game not found: " + gameId
            );
        }

        return game;
    }

    private PlayerType resolvePlayerType(PlayerType playerType) {
        return playerType == null
                ? PlayerType.HUMAN
                : playerType;
    }
}