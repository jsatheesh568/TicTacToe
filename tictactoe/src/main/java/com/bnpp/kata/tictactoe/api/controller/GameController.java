
package com.bnpp.kata.tictactoe.api.controller;

import com.bnpp.kata.tictactoe.api.dto.*;
import com.bnpp.kata.tictactoe.application.service.GameApplicationService;
import com.bnpp.kata.tictactoe.domain.model.Game;
import com.bnpp.kata.tictactoe.domain.model.Mark;
import com.bnpp.kata.tictactoe.domain.model.Move;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameApplicationService gameApplicationService;

    public GameController(GameApplicationService gameApplicationService) {
        this.gameApplicationService = gameApplicationService;
    }

    @PostMapping
    public ResponseEntity<GameStartResponse> startGame(
            @Valid @RequestBody StartGameRequest request
    ) {
        var gameSession = gameApplicationService.startGame(
                request.getPlayerXName(),
                request.getPlayerXType(),
                request.getPlayerOName(),
                request.getPlayerOType()
        );

        Game game = gameSession.getGame();

        GameStartResponse response = new GameStartResponse(
                gameSession.getGameId(),
                game.getPlayer(Mark.X).getName(),
                game.getPlayer(Mark.X).getMark(),
                game.getPlayer(Mark.X).getType(),
                game.getPlayer(Mark.O).getName(),
                game.getPlayer(Mark.O).getMark(),
                game.getPlayer(Mark.O).getType(),
                game.getCurrentPlayer().getName(),
                game.getCurrentMark(),
                game.getStatus()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{gameId}/moves")
    public ResponseEntity<GameMoveResponse> makeMove(
            @PathVariable String gameId,
            @Valid @RequestBody MakeMoveRequest request
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

    @GetMapping("/{gameId}")
    public ResponseEntity<GameStateResponse> getGameState(
            @PathVariable String gameId
    ) {
        Game game = gameApplicationService.getGame(gameId);

        GameStateResponse response = new GameStateResponse(
                gameId,
                game.getPlayer(Mark.X).getName(),
                game.getPlayer(Mark.X).getMark(),
                game.getPlayer(Mark.X).getType(),
                game.getPlayer(Mark.O).getName(),
                game.getPlayer(Mark.O).getMark(),
                game.getPlayer(Mark.O).getType(),
                buildBoard(game),
                game.getCurrentPlayer().getName(),
                game.getCurrentMark(),
                game.getStatus(),
                buildMoveHistory(game)
        );

        return ResponseEntity.ok(response);
    }

    private List<List<String>> buildBoard(Game game) {
        List<List<String>> board = new ArrayList<>();

        for (int row = 0; row < 3; row++) {
            List<String> boardRow = new ArrayList<>();

            for (int column = 0; column < 3; column++) {
                Mark mark = game.getMarkAt(row, column);
                boardRow.add(mark == null ? null : mark.name());
            }

            board.add(boardRow);
        }

        return board;
    }

    private List<MoveHistoryResponse> buildMoveHistory(Game game) {
        List<MoveHistoryResponse> moveHistory = new ArrayList<>();

        for (Move move : game.getMoveHistory()) {
            moveHistory.add(
                    new MoveHistoryResponse(
                            move.getMark(),
                            move.getRow(),
                            move.getColumn()
                    )
            );
        }

        return moveHistory;
    }
}
