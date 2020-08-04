package com.mccarthy.api.validation;

import com.mccarthy.api.error.exceptions.DuplicateSymbolValidationException;
import com.mccarthy.api.error.exceptions.SymbolException;
import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import com.mccarthy.api.service.dao.DataAccess;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SymbolValidationService {
    protected DataAccess dataAccess;

    protected SymbolValidationService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }


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

    public void validatePortfolioContainsSymbol(String portfolioId, String symbolToFind) {
        Portfolio portfolio = dataAccess.getPortfolio(portfolioId);
        List<Symbol> symbols = portfolio.getSymbols();
        if ((symbols == null || symbols.isEmpty()) || !containsSymbol(portfolio, symbolToFind)) {
            throw new SymbolException("Symbol " + symbolToFind + " does not exist in portfolio " + portfolioId);
        }
    }

    protected boolean containsSymbol(Portfolio portfolio, String symbolToFind) {
        for (Symbol symbol : portfolio.getSymbols()) {
            if (symbolToFind.equals(symbol.getSymbol())) {
                return true;
            }
        }

        return false;
    }
}
