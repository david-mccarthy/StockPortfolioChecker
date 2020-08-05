package com.mccarthy.api.error.exceptions;

/**
 * Exception to be thrown when there is an issue with a given symbol.
 */
public class SymbolException extends RuntimeException {
    public SymbolException(String message) {
        super(message);
    }
}
