
package com.bnpp.kata.tictactoe.application.exception;

public class InvalidGameOperationException extends RuntimeException {

    public InvalidGameOperationException(String message) {
        super(message);
    }
}
