package com.bnpp.kata.tictactoe.api.controller;

import com.bnpp.kata.tictactoe.api.dto.GameMoveResponse;
import com.bnpp.kata.tictactoe.api.dto.GameStartResponse;
import com.bnpp.kata.tictactoe.api.dto.MakeMoveRequest;
import com.bnpp.kata.tictactoe.api.dto.StartGameRequest;
import com.bnpp.kata.tictactoe.application.service.GameApplicationService;
import com.bnpp.kata.tictactoe.application.service.GameSession;
import com.bnpp.kata.tictactoe.domain.model.Game;
import com.bnpp.kata.tictactoe.domain.model.Mark;
import com.bnpp.kata.tictactoe.domain.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private static final int BOARD_SIZE = 3;

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
        GameSession session = gameApplicationService.startGame(
                request.getPlayerXName(),
                request.getPlayerXType(),
                request.getPlayerOName(),
                request.getPlayerOType()
        );

        Game game = session.getGame();

        Player playerX = game.getPlayer(Mark.X);
        Player playerO = game.getPlayer(Mark.O);

        GameStartResponse response = new GameStartResponse(
                session.getGameId(),
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

    @PostMapping("/{gameId}/moves")
    public ResponseEntity<GameMoveResponse> makeMove(
            @PathVariable String gameId,
            @RequestBody MakeMoveRequest request
    ) {
        Game game = gameApplicationService.playMove(
                gameId,
                request.getRow(),
                request.getColumn()
        );

        GameMoveResponse response = new GameMoveResponse(
                gameId,
                buildBoard(game),
                game.getCurrentPlayer().getName(),
                game.getCurrentMark(),
                game.getStatus()
        );

        return ResponseEntity.ok(response);
    }

    private List<List<String>> buildBoard(Game game) {
        List<List<String>> board = new ArrayList<>();

        for (int row = 0; row < BOARD_SIZE; row++) {
            List<String> boardRow = new ArrayList<>();

            for (int column = 0; column < BOARD_SIZE; column++) {
                Mark mark = game.getMarkAt(row, column);

                boardRow.add(
                        mark == null ? null : mark.name()
                );
            }

            board.add(boardRow);
        }

        return board;
    }
}