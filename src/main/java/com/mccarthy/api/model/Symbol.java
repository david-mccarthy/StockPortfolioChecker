package com.mccarthy.api.model;

import java.math.BigDecimal;

/**
 * Symbol model.
 */
public class Symbol {
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
