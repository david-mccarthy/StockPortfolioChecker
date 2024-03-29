package com.mccarthy.api.controller;

import com.mccarthy.api.error.ErrorCodes;
import com.mccarthy.api.error.ErrorService;
import com.mccarthy.api.error.exceptions.*;
import com.mccarthy.api.model.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * Controller to handle exceptions raised within the application.
 */
@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {
    @Autowired
    protected ErrorService errorService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleApiGenericErrors(Exception ex) {
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.UNKNOWN_ERROR_OCCURED);

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoPortfolioException.class)
    public ResponseEntity<ApiError> handleNoPortfolioErrors(NoPortfolioException ex) {
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.PORTFOLIO_NOT_FOUND);

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ApiError> handleExternalServiceErrors(ExternalServiceException ex) {
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.EXTERNAL_SERVICE_ERROR);

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateSymbolValidationException.class)
    public ResponseEntity<ApiError> handleDuplicateSymbolsInPortfolioErrors(DuplicateSymbolValidationException ex) {
        List<String> symbols = ex.getSymbols();
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.DUPLICATE_SYMBOLS_IN_REQUEST, symbols);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SymbolException.class)
    public ResponseEntity<ApiError> handleSymbolValidationErrors(SymbolException ex) {
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.SYMBOL_VALIDATION_ERROR);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSymbolException.class)
    public ResponseEntity<ApiError> handleSymbolValidationErrors(InvalidSymbolException ex) {
        List<String> symbols = ex.getSymbols();
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.INVALID_SYMBOL_ERROR, symbols);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
