package com.mccarthy.api.error.exceptions;

import java.util.List;

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
