package com.mccarthy.api.controller;

import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.service.portfolio.CreatePortfolioService;
import com.mccarthy.api.service.portfolio.DeletePortfolioService;
import com.mccarthy.api.service.portfolio.GetPortfolioService;
import com.mccarthy.api.service.portfolio.UpdatePortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PortfolioController {
    @Autowired
    protected DataAccess dataAccess;

    @Autowired
    protected CreatePortfolioService createPortfolioService;

    @Autowired
    protected UpdatePortfolioService updatePortfolioService;

    @Autowired
    protected DeletePortfolioService deletePortfolioService;

    @Autowired
    protected GetPortfolioService getPortfolioService;

    @RequestMapping(method = RequestMethod.POST, path = "portfolio", produces = "application/json")
    public ResponseEntity<Portfolio> createPortfolio() {
        return createPortfolioService.createPortfolio();
    }

    @RequestMapping(method = RequestMethod.POST, path = "portfolio/{portfolioId}", produces = "application/json")
    public ResponseEntity<Void> addPortfolioItem(@PathVariable String portfolioId, @RequestBody AddItemInput input) {
        return updatePortfolioService.updatePortfolio(portfolioId, input);
    }

    @RequestMapping(method = RequestMethod.GET, path = "portfolio/{portfolioId}", produces = "application/json")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable String portfolioId) {
        return getPortfolioService.getPortfolio(portfolioId);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "portfolio/{portfolioId}", produces = "application/json")
    public ResponseEntity<Void> deletePortfolio(@PathVariable String portfolioId) {
        return deletePortfolioService.deletePortfolio(portfolioId);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "portfolio/{portfolioId}/symbol/{symbol}", produces = "application/json")
    public ResponseEntity<Void> deletePortfolioSymbols(@PathVariable String portfolioId, @PathVariable String symbol) {
        return deletePortfolioService.deleteSymbol(portfolioId, symbol);
    }
}
