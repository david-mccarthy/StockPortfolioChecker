package com.mccarthy.api.service.dao;

import com.mccarthy.api.model.Portfolio;

public interface DataAccess {

    Portfolio getPortfolio(String id);

    void savePortfolio(Portfolio portfolio);

    void deletePortfolio(String id);

    void deleteSymbol(String portfolioId, String symbol);

    boolean hasPortfolio(String id);
}
