package com.mccarthy.api.service.portfolio;

import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.validation.SymbolValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UpdatePortfolioService {
    protected final DataAccess dataAccess;
    protected final SymbolValidationService symbolValidationService;

    public UpdatePortfolioService(DataAccess dataAccess, SymbolValidationService symbolValidationService) {
        this.dataAccess = dataAccess;
        this.symbolValidationService = symbolValidationService;
    }

    public ResponseEntity<Void> updatePortfolio(String id, AddItemInput input) {
        //Validate that the symbol is real.
        Portfolio portfolio = dataAccess.getPortfolio(id);
        symbolValidationService.validatePortfolioDoesNotContainNewSymbols(id, input);

        for (AddItemInput.SymbolInput symbol : input.getSymbols()) {
            Symbol newSymbol = new Symbol();
            String name = symbol.getName();
            newSymbol.setSymbol(name);
            newSymbol.setVolume(symbol.getVolume());
            portfolio.addSymbol(newSymbol);
        }
        dataAccess.savePortfolio(portfolio);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
