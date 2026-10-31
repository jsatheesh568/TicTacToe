package com.bnpp.kata.tictactoe.api.controller;

import com.bnpp.kata.tictactoe.api.dto.GameStartResponse;
import com.bnpp.kata.tictactoe.api.dto.StartGameRequest;
import com.bnpp.kata.tictactoe.application.service.GameApplicationService;
import com.bnpp.kata.tictactoe.domain.model.Game;
import com.bnpp.kata.tictactoe.domain.model.Mark;
import com.bnpp.kata.tictactoe.domain.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameApplicationService gameApplicationService;

    public GameController(
            GameApplicationService gameApplicationService
    ) {
        this.gameApplicationService = gameApplicationService;
    }

    @PostMapping
    public ResponseEntity<GameStartResponse> startGame(
            @RequestBody StartGameRequest request
    ) {
        String gameId = gameApplicationService.startGame(
                request.getPlayerXName(),
                request.getPlayerXType(),
                request.getPlayerOName(),
                request.getPlayerOType()
        );

        Game game = gameApplicationService.getGame(gameId);

        Player playerX = game.getPlayer(Mark.X);
        Player playerO = game.getPlayer(Mark.O);

        GameStartResponse response = new GameStartResponse(
                gameId,
                playerX.getName(),
                playerX.getMark(),
                playerX.getType(),
                playerO.getName(),
                playerO.getMark(),
                playerO.getType(),
                game.getCurrentPlayer().getName(),
                game.getCurrentMark(),
                game.getStatus()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}