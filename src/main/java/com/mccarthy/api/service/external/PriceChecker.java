package com.mccarthy.api.service.external;

import java.math.BigDecimal;

/**
 * Interface defining the operations of a price checker service.
 */
public interface PriceChecker {

    /**
     * Get the price associated with the given symbol.
     *
     * @param symbol Symbol to price check.
     * @return Price of the given symbol.
     */
    BigDecimal getPrice(String symbol);

    /**
     * Check if the given symbol is valid in the external system.
     *
     * @param symbol Symbol to check.
     * @return true if the symbol is valid in the external system.
     */
    boolean isValidSymbol(String symbol);
}
