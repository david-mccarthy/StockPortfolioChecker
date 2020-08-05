package com.mccarthy.api.error.exceptions;

import java.util.List;

/**
 * Exception to be thrown when trying to add a symbol to a portfolio, where it already exists.
 */
public class DuplicateSymbolValidationException extends RuntimeException {
    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }

    private List<String> symbols;

    public DuplicateSymbolValidationException(String message, List<String> symbols) {
        super(message);
        this.symbols = symbols;
    }
}
