package com.mccarthy.api.error.exceptions;

/**
 * Exception to be thrown when a requested portfolio cannot be found.
 */
public class NoPortfolioException extends RuntimeException {
    public NoPortfolioException(String message) {
        super(message);
    }
}
