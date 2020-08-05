package com.mccarthy.api.service.portfolio;

import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.validation.PortfolioValidationService;
import com.mccarthy.api.validation.SymbolValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service for deleting a portfolio, or its symbols.
 */
@Service
public class DeletePortfolioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeletePortfolioService.class);
    protected final DataAccess dataAccess;
    protected final PortfolioValidationService portfolioValidationService;
    protected final SymbolValidationService symbolValidationService;

    public DeletePortfolioService(DataAccess dataAccess, PortfolioValidationService portfolioValidationService,
                                  SymbolValidationService symbolValidationService) {
        this.dataAccess = dataAccess;
        this.portfolioValidationService = portfolioValidationService;
        this.symbolValidationService = symbolValidationService;
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
        dataAccess.deletePortfolio(portfolioId);

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
        dataAccess.deleteSymbol(portfolioId, symbol);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
