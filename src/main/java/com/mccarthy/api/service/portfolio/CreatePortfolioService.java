package com.mccarthy.api.service.portfolio;

import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.service.dao.DataAccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Create a portfolio.
 */
@Service
public class CreatePortfolioService {
    protected final DataAccess dataAccess;

    public CreatePortfolioService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public ResponseEntity<Portfolio> createPortfolio() {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(UUID.randomUUID().toString());
        dataAccess.savePortfolio(portfolio);

        return new ResponseEntity<>(portfolio, HttpStatus.CREATED);
    }
}
