package com.bnpp.kata.tictactoe.application.service;

import com.bnpp.kata.tictactoe.application.exception.GameNotFoundException;
import com.bnpp.kata.tictactoe.application.exception.InvalidGameOperationException;
import com.bnpp.kata.tictactoe.domain.model.Game;
import com.bnpp.kata.tictactoe.domain.model.GameStatus;
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

    public GameSession startGame(
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

        return new GameSession(gameId, game);
    }

    public Game getGame(String gameId) {
        Game game = games.get(gameId);

        if (game == null) {
            throw new GameNotFoundException(
                    "Game not found: " + gameId
            );
        }

        return game;
    }

    public Game playMove(
            String gameId,
            int row,
            int column
    ) {
        Game game = getGame(gameId);

        try {
            if (game.getCurrentPlayer().isComputer()) {
                throw new InvalidGameOperationException(
                        "It is the computer player's turn"
                );
            }

            game.play(row, column);

            if (game.getStatus() == GameStatus.IN_PROGRESS
                    && game.getCurrentPlayer().isComputer()) {
                game.playComputerMove();
            }

            return game;

        } catch (InvalidGameOperationException exception) {
            throw exception;
        } catch (IllegalStateException exception) {
            throw new InvalidGameOperationException(
                    exception.getMessage()
            );
        }
    }

    private PlayerType resolvePlayerType(PlayerType playerType) {
        return playerType == null
                ? PlayerType.HUMAN
                : playerType;
    }
}
