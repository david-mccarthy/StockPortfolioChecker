package com.mccarthy.api.error;

import com.mccarthy.api.error.exceptions.DuplicateSymbolValidationException;
import com.mccarthy.api.error.exceptions.ExternalServiceException;
import com.mccarthy.api.error.exceptions.NoPortfolioException;
import com.mccarthy.api.error.exceptions.SymbolException;
import com.mccarthy.api.model.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {
    @Autowired
    protected ErrorService errorService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleApiGenericErrors(Exception ex) {
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.UNKNOWN_ERROR_OCCURED, "Unknown error in application");

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoPortfolioException.class)
    public ResponseEntity<ApiError> handleNoPortfolioErrors(NoPortfolioException ex) {
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.PORTFOLIO_NOT_FOUND, "Portfolio with provided id does not exist.");

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ApiError> handleExternalServiceErrors(ExternalServiceException ex) {
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.EXTERNAL_SERVICE_ERROR, "Error in attempting to connect with external price supplier.");

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateSymbolValidationException.class)
    public ResponseEntity<ApiError> handleDuplicateSymbolsInPortfolioErrors(DuplicateSymbolValidationException ex) {
        List<String> symbols = ex.getSymbols();
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.DUPLICATE_SYMBOLS_IN_REQUEST, "Duplicate symbols in request: " + symbols);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SymbolException.class)
    public ResponseEntity<ApiError> handleSymbolValidationErrors(SymbolException ex) {
        ApiError apiError = errorService.createApiError(ex, ErrorCodes.SYMBOL_VALIDATION_ERROR, "Validation error found with symbols.");

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
