package com.mccarthy.api.error.exceptions;

/**
 * Exception to be thrown when an external service encounters an error.
 */
public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String message) {
        super(message);
    }
}
