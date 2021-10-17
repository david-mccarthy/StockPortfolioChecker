package com.mccarthy.api.service;

import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import com.mccarthy.api.service.dao.DataAccessService;
import com.mccarthy.api.service.external.PriceCheckerService;
import com.mccarthy.api.validation.PortfolioValidationService;
import com.mccarthy.api.validation.SymbolValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PortfolioManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioManagerService.class);

    protected final DataAccessService dataAccessService;
    protected final PortfolioValidationService portfolioValidationService;
    protected final PriceCheckerService priceCheckerService;
    protected final SymbolValidationService symbolValidationService;

    public PortfolioManagerService(DataAccessService dataAccessService, PortfolioValidationService portfolioValidationService,PriceCheckerService priceCheckerService, SymbolValidationService symbolValidationService) {
        this.dataAccessService = dataAccessService;
        this.portfolioValidationService = portfolioValidationService;
        this.priceCheckerService = priceCheckerService;
        this.symbolValidationService = symbolValidationService;
    }

    /**
     * Create a portfolio.
     *
     * @return An empty portfolio.
     */
    public ResponseEntity<Portfolio> createPortfolio() {
        LOGGER.info("Creating portfolio");
        Portfolio portfolio = new Portfolio();
        portfolio.setId(UUID.randomUUID().toString());
        dataAccessService.savePortfolio(portfolio);

        return new ResponseEntity<>(portfolio, HttpStatus.CREATED);
    }

    /**
     * Retrieve the portfolio identified by the given id.
     *
     * @param id Id of the portfolio to retrieve.
     * @return Portfolio.
     */
    public ResponseEntity<Portfolio> getPortfolio(String id) {
        LOGGER.info("Retrieving portfolio with id " + id);
        Portfolio portfolio = dataAccessService.getPortfolio(id);
        BigDecimal totalValue = BigDecimal.ZERO;
        for (Symbol price : portfolio.getSymbols()) {
            String symbol = price.getName();
            BigDecimal currentPrice = priceCheckerService.getPrice(symbol);
            price.setPrice(currentPrice);

            long volume = price.getVolume();
            totalValue = totalValue.add(currentPrice.multiply(BigDecimal.valueOf(volume)));
        }

        portfolio.setTotalValue(totalValue);

        return new ResponseEntity<>(portfolio, HttpStatus.OK);
    }

    /**
     * Add the given symbols from the input to a portfolio identified by the given id.
     *
     * @param id    Id of the portfolio.
     * @param input Input containing the new symbols to add.
     * @return Void response entity.
     */
    public ResponseEntity<Void> updatePortfolio(String id, AddItemInput input) {
        LOGGER.info("Updating portfolio with id " + id + " with new symbols");
        Portfolio portfolio = dataAccessService.getPortfolio(id);
        symbolValidationService.validatePortfolioDoesNotContainNewSymbols(id, input);
        symbolValidationService.validateSymbolExistsInExternalSystem(input);
        for (AddItemInput.SymbolInput symbol : input.getSymbols()) {
            Symbol newSymbol = new Symbol();
            String name = symbol.getName();
            newSymbol.setName(name);
            newSymbol.setVolume(symbol.getVolume());
            portfolio.addSymbol(newSymbol);
        }
        dataAccessService.savePortfolio(portfolio);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete the portfolio identified by the given id.
     *
     * @param portfolioId Id of the portfolio we want to remove.
     * @return A void response entity.
     */
    public ResponseEntity<Void> deletePortfolio(String portfolioId) {
        LOGGER.info("Deleting portfolio with id " + portfolioId);
        portfolioValidationService.validatePortfolioExists(portfolioId);
        dataAccessService.deletePortfolio(portfolioId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * Delete a given symbol from the portfolio identified by the given id.
     *
     * @param portfolioId Id of the portfolio.
     * @param symbol      Symbol to remove.
     * @return A void response entity.
     */
    public ResponseEntity<Void> deleteSymbol(String portfolioId, String symbol) {
        LOGGER.info("Deleting symbol " + symbol + " from portfolio with id " + portfolioId);
        portfolioValidationService.validatePortfolioExists(portfolioId);
        symbolValidationService.validatePortfolioContainsSymbol(portfolioId, symbol);
        dataAccessService.deleteSymbol(portfolioId, symbol);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
