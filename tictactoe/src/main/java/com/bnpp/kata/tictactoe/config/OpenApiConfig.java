
package com.bnpp.kata.tictactoe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ticTacToeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tic Tac Toe Game API")
                        .description("""
                                REST API for playing Tic Tac Toe.

                                Supports human and computer players,
                                game creation, making moves and
                                retrieving the current game state.
                                """)
                        .version("1.0.0"))
                .tags(List.of(
                        new Tag()
                                .name("Games")
                                .description("Tic Tac Toe game operations")
                ));
    }

    @Bean
    public OpenApiCustomizer gameApiCustomizer() {
        return openApi -> {

            // POST /api/games
            if (openApi.getPaths().get("/api/games") != null) {
                var operation = openApi.getPaths()
                        .get("/api/games")
                        .getPost();

                if (operation != null) {
                    operation.setTags(List.of("Games"));
                    operation.setSummary("Create a new game");
                    operation.setDescription(
                            "Creates a new Tic Tac Toe game with two players. " +
                                    "Players can be HUMAN or COMPUTER."
                    );

                    addErrorResponses(operation.getResponses(), true);
                }
            }

            // POST /api/games/{gameId}/moves
            if (openApi.getPaths().get("/api/games/{gameId}/moves") != null) {
                var operation = openApi.getPaths()
                        .get("/api/games/{gameId}/moves")
                        .getPost();

                if (operation != null) {
                    operation.setTags(List.of("Games"));
                    operation.setSummary("Make a game move");
                    operation.setDescription(
                            "Places the current player's mark at the requested " +
                                    "board position. A computer player responds automatically " +
                                    "when it is configured as the next player."
                    );

                    addErrorResponses(operation.getResponses(), false);
                }
            }

            // GET /api/games/{gameId}
            if (openApi.getPaths().get("/api/games/{gameId}") != null) {
                var operation = openApi.getPaths()
                        .get("/api/games/{gameId}")
                        .getGet();

                if (operation != null) {
                    operation.setTags(List.of("Games"));
                    operation.setSummary("Retrieve game state");
                    operation.setDescription(
                            "Retrieves player information, board state, current " +
                                    "player, game status and move history."
                    );

                    operation.getResponses().addApiResponse(
                            "404",
                            new ApiResponse()
                                    .description("Game not found")
                    );
                }
            }
        };
    }

    private void addErrorResponses(
            ApiResponses responses,
            boolean includeNotFound
    ) {
        responses.addApiResponse(
                "400",
                new ApiResponse()
                        .description("Invalid request or validation failure")
        );

        responses.addApiResponse(
                "409",
                new ApiResponse()
                        .description("Invalid game operation")
        );

        if (includeNotFound) {
            responses.addApiResponse(
                    "404",
                    new ApiResponse()
                            .description("Game not found")
            );
        }
    }
}
