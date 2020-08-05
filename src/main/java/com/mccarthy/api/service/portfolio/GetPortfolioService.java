package com.mccarthy.api.service.portfolio;

import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.service.external.PriceChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service to retrieve a portfolio from the cache.
 */
@Service
public class GetPortfolioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetPortfolioService.class);
    protected DataAccess dataAccess;
    protected final PriceChecker priceChecker;

    public GetPortfolioService(DataAccess dataAccess, PriceChecker priceChecker) {
        this.dataAccess = dataAccess;
        this.priceChecker = priceChecker;
    }

    /**
     * Retrieve the portfolio identified by the given id.
     *
     * @param id Id of the portfolio to retrieve.
     * @return Portfolio.
     */
    public ResponseEntity<Portfolio> getPortfolio(String id) {
        LOGGER.info("Retrieving portfolio with id " + id);
        Portfolio portfolio = dataAccess.getPortfolio(id);
        BigDecimal totalValue = BigDecimal.ZERO;
        for (Symbol price : portfolio.getSymbols()) {
            String symbol = price.getSymbol();
            BigDecimal currentPrice = priceChecker.getPrice(symbol);
            price.setPrice(currentPrice);

            long volume = price.getVolume();
            totalValue = totalValue.add(currentPrice.multiply(BigDecimal.valueOf(volume)));
        }

        portfolio.setTotalValue(totalValue);

        return new ResponseEntity<>(portfolio, HttpStatus.OK);
    }
}
