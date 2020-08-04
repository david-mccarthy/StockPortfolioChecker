package com.mccarthy.api.service.external;

import java.math.BigDecimal;

public interface PriceChecker {

    BigDecimal getPrice(String symbol);
}
