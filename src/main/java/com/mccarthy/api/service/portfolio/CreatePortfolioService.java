package com.mccarthy.api.service.portfolio;

import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.service.dao.DataAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service to create a portfolio.
 */
@Service
public class CreatePortfolioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreatePortfolioService.class);
    protected final DataAccessService dataAccessService;

    public CreatePortfolioService(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
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
}
