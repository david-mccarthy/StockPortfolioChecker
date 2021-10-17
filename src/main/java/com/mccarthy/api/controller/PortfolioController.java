package com.mccarthy.api.controller;

import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.service.PortfolioManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PortfolioController {
    @Autowired
    private PortfolioManagerService portfolioManagerService;

    @RequestMapping(method = RequestMethod.POST, path = "portfolio", produces = "application/json")
    public ResponseEntity<Portfolio> createPortfolio() {
        return portfolioManagerService.createPortfolio();
    }

    @RequestMapping(method = RequestMethod.POST, path = "portfolio/{portfolioId}", produces = "application/json")
    public ResponseEntity<Void> addPortfolioItem(@PathVariable String portfolioId, @RequestBody AddItemInput input) {
        return portfolioManagerService.updatePortfolio(portfolioId, input);
    }

    @RequestMapping(method = RequestMethod.GET, path = "portfolio/{portfolioId}", produces = "application/json")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable String portfolioId) {
        return portfolioManagerService.getPortfolio(portfolioId);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "portfolio/{portfolioId}", produces = "application/json")
    public ResponseEntity<Void> deletePortfolio(@PathVariable String portfolioId) {
        return portfolioManagerService.deletePortfolio(portfolioId);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "portfolio/{portfolioId}/symbol/{symbol}", produces = "application/json")
    public ResponseEntity<Void> deletePortfolioSymbols(@PathVariable String portfolioId, @PathVariable String symbol) {
        return portfolioManagerService.deleteSymbol(portfolioId, symbol);
    }
}
