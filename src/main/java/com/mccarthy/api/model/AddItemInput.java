package com.mccarthy.api.model;

import java.util.List;

/**
 * Input model for adding symbols to a portfolio.
 */
public class AddItemInput {
    private List<SymbolInput> symbols;

    public List<SymbolInput> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<SymbolInput> symbols) {
        this.symbols = symbols;
    }

    /**
     * Symbol information to add to a portfolio.
     */
    public static class SymbolInput {
        private String name;
        private int volume;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }
    }
}
