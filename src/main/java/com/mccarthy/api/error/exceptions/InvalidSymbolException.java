package com.mccarthy.api.error.exceptions;

import java.util.List;

/**
 * Exception to be thrown when an invalid symbol is sent in.
 */
public class InvalidSymbolException extends RuntimeException {
    private List<String> symbols;

    public InvalidSymbolException(String message, List<String> symbols) {
        super(message);
        this.symbols = symbols;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }
}
