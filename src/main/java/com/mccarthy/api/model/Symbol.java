package com.mccarthy.api.model;

import java.math.BigDecimal;

/**
 * Symbol model.
 */
public class Symbol {
    private String symbol;
    private BigDecimal price;
    private int volume;
    private BigDecimal totalValue;

    public BigDecimal getTotalValue() {
        return price.multiply(BigDecimal.valueOf(this.getVolume()));
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
