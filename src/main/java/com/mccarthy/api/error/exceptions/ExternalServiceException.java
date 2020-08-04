package com.mccarthy.api.error.exceptions;

/**
 * Exception to be thrown when
 */
public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String message) {
        super(message);
    }
}
