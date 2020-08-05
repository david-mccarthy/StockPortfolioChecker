package com.mccarthy.api.service.portfolio;

import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.validation.SymbolValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service to update a portfolio by adding symbols to it.
 */
@Service
public class UpdatePortfolioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePortfolioService.class);
    protected final DataAccess dataAccess;
    protected final SymbolValidationService symbolValidationService;

    public UpdatePortfolioService(DataAccess dataAccess, SymbolValidationService symbolValidationService) {
        this.dataAccess = dataAccess;
        this.symbolValidationService = symbolValidationService;
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
        Portfolio portfolio = dataAccess.getPortfolio(id);
        symbolValidationService.validatePortfolioDoesNotContainNewSymbols(id, input);
        symbolValidationService.validateSymbolExistsInExternalSystem(input);
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
