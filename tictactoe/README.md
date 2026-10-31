# Tic Tac Toe

A Tic Tac Toe REST API built using Java 21 and Spring Boot 4.

The application supports human-versus-human and human-versus-computer
gameplay, move history, undo/redo, game state retrieval and API
request validation.

## Technology Stack

- Java 21
- Spring Boot 4.0.0
- Spring MVC
- Spring Validation
- Maven
- JUnit 5
- Mockito
- JaCoCo
- Springdoc OpenAPI
- Swagger UI

## Features

- Create a new Tic Tac Toe game.
- Support HUMAN and COMPUTER players.
- Place player marks on a 3x3 board.
- Automatically execute computer-player moves.
- Detect horizontal, vertical and diagonal wins.
- Detect draw conditions.
- Prevent moves after game completion.
- Restart a game.
- Maintain move history.
- Undo and redo moves.
- Retrieve the complete game state.
- Validate incoming API requests.
- Return standardized API error responses.
- Interactive API documentation through Swagger UI.

## Project Structure

```text
src
├── main
│   ├── java/com/bnpp/kata/tictactoe
│   │   ├── api
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   └── exception
│   │   ├── application
│   │   │   ├── exception
│   │   │   └── service
│   │   ├── config
│   │   └── domain
│   │       ├── model
│   │       └── service
│   └── resources
└── test
    └── java/com/bnpp/kata/tictactoe
```

## Architecture

The application follows a layered architecture.

### Domain Layer

Contains the game business rules:

- Game
- Board
- Cell
- Player
- Move
- Position
- GameStatus
- Mark
- PlayerType
- ComputerPlayer

### Application Layer

Coordinates game operations and manages game sessions.

### API Layer

Exposes REST endpoints, validates requests and translates application
exceptions into HTTP responses.

### Configuration Layer

Contains application configuration, including OpenAPI documentation.

## Prerequisites

- JDK 21
- Maven 3.9+
- Git

## Build and Run

Clone the repository and navigate to the project directory.

```bash
git clone <repository-url>
cd tictactoe
```

Build the application:

```bash
mvn clean install
```

Run all tests:

```bash
mvn clean test
```

Run coverage verification:

```bash
mvn clean verify
```

Start the application:

```bash
mvn spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## REST API

### 1. Create a Game

**POST** `/api/games`

Request:

```json
{
  "playerXName": "Alice",
  "playerXType": "HUMAN",
  "playerOName": "Computer",
  "playerOType": "COMPUTER"
}
```

### 2. Make a Move

**POST** `/api/games/{gameId}/moves`

Request:

```json
{
  "row": 0,
  "column": 0
}
```

Rows and columns use zero-based indexing:

- Row: 0 to 2
- Column: 0 to 2

When the next player is a computer, its move is executed automatically.

### 3. Retrieve Game State

**GET** `/api/games/{gameId}`

Returns:

- Game ID
- Both player details
- Current board
- Current player
- Current mark
- Game status
- Move history

## API Error Handling

| HTTP Status | Description |
|---|---|
| 400 | Invalid request or validation failure |
| 404 | Game not found |
| 409 | Invalid game operation |

Example error response:

```json
{
  "status": 404,
  "error": "GAME_NOT_FOUND",
  "message": "Game not found",
  "path": "/api/games/invalid-id"
}
```

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

OpenAPI YAML:

```text
http://localhost:8080/v3/api-docs.yaml
```

## Code Coverage

JaCoCo generates coverage reports after:

```bash
mvn clean verify
```

HTML report:

```text
target/site/jacoco/index.html
```

The configured minimum coverage thresholds are:

- Instruction coverage: 70%
- Branch coverage: 60%

## Testing

The project includes unit tests and REST API integration tests covering:

- Board initialization and mark placement.
- Player turn management.
- Winning and draw conditions.
- Game lifecycle and move restrictions.
- Computer-player behavior.
- Undo and redo functionality.
- Application service operations.
- REST API request validation.
- API exception handling.
- Complete game workflows.
- OpenAPI specification availability.

## License

This project is intended for learning and demonstrating
TDD, clean code, SOLID principles and layered application design.
