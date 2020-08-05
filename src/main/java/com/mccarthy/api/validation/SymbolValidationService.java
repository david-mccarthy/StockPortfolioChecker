package com.mccarthy.api.validation;

import com.mccarthy.api.error.exceptions.DuplicateSymbolValidationException;
import com.mccarthy.api.error.exceptions.InvalidSymbolException;
import com.mccarthy.api.error.exceptions.SymbolException;
import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.service.external.PriceChecker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to validate symbols for a portfolio.
 */
@Service
public class SymbolValidationService {
    protected DataAccess dataAccess;
    protected PriceChecker priceChecker;

    public SymbolValidationService(DataAccess dataAccess, PriceChecker priceChecker) {
        this.dataAccess = dataAccess;
        this.priceChecker = priceChecker;
    }

    /**
     * Validate that a given portfolio does not already contain one of the new symbols we want to add.
     *
     * @param portfolioId Id of our existing portfolio.
     * @param input       Input containing the new symbols to add.
     */
    public void validatePortfolioDoesNotContainNewSymbols(String portfolioId, AddItemInput input) {
        Portfolio portfolio = dataAccess.getPortfolio(portfolioId);
        List<String> duplicateSymbols = new ArrayList<>();

        for (AddItemInput.SymbolInput symbolInput : input.getSymbols()) {
            String name = symbolInput.getName();
            if (containsSymbol(portfolio, name)) {
                duplicateSymbols.add(name);
            }
        }

        if (!duplicateSymbols.isEmpty()) {
            throw new DuplicateSymbolValidationException("Duplicate symbols found in portfolio " + portfolioId, duplicateSymbols);
        }
    }

    /**
     * Validate that a given portfolio does contain a given symbol, for example before trying to remove it.
     *
     * @param portfolioId  Id of the portfolio.
     * @param symbolToFind Symbol to look for.
     */
    public void validatePortfolioContainsSymbol(String portfolioId, String symbolToFind) {
        Portfolio portfolio = dataAccess.getPortfolio(portfolioId);
        List<Symbol> symbols = portfolio.getSymbols();
        if ((symbols == null || symbols.isEmpty()) || !containsSymbol(portfolio, symbolToFind)) {
            throw new SymbolException("Symbol " + symbolToFind + " does not exist in portfolio " + portfolioId);
        }
    }

    /**
     * Validate that the given symbols is valid against the external system.
     *
     * @param input Input containing the new symbols to add.
     */
    public void validateSymbolExistsInExternalSystem(AddItemInput input) {
        List<String> errors = new ArrayList<>();
        for (AddItemInput.SymbolInput symbol : input.getSymbols()) {
            if (!priceChecker.isValidSymbol(symbol.getName())) {
                errors.add(symbol.getName());
            }
        }

        if (!errors.isEmpty()) {
            throw new InvalidSymbolException("Provided symbols are not valid against the supplier.", errors);
        }
    }

    /**
     * Check if the portfolio contains a given symbol.
     *
     * @param portfolio    Portfolio.
     * @param symbolToFind Symbol to check for,
     * @return true if the symbol exists in the portfolio.
     */
    protected boolean containsSymbol(Portfolio portfolio, String symbolToFind) {
        for (Symbol symbol : portfolio.getSymbols()) {
            if (symbolToFind.equals(symbol.getSymbol())) {
                return true;
            }
        }

        return false;
    }
}
