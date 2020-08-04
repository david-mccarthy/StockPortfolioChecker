package com.mccarthy.api.validation;

import com.mccarthy.api.error.exceptions.NoPortfolioException;
import com.mccarthy.api.service.dao.DataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PortfolioValidationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioValidationService.class);
    protected final DataAccess dataAccess;

    public PortfolioValidationService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void validatePortfolioExists(String portfolioId) {
        if (!dataAccess.hasPortfolio(portfolioId)) {
            LOGGER.debug("Portfolio with id " + portfolioId + " does not exist.");
            throw new NoPortfolioException("Validation error: Portfolio with id " + portfolioId + " does not exist.");
        }
    }
}
