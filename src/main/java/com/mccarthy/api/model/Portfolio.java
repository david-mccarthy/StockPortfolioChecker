package com.mccarthy.api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Model of a portfolio.
 */
public class Portfolio {
    protected String id;
    protected List<Symbol> symbols;
    protected BigDecimal totalValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Symbol> getSymbols() {
        return this.symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public void addSymbol(Symbol symbol) {
        if (this.symbols == null) {
            this.symbols = new ArrayList<>();
        }
        this.symbols.add(symbol);
    }

    public BigDecimal getTotalValue() {
        return this.totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
}
