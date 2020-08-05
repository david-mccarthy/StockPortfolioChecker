package com.mccarthy.api.unit.service.portfolio;

import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.service.dao.DataAccessService;
import com.mccarthy.api.service.portfolio.CreatePortfolioService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreatePortfolioServiceTest {
    @Mock
    protected DataAccessService dataAccessService;
    protected CreatePortfolioService createPortfolioService;

    @Before
    public void setup() {
        createPortfolioService = new CreatePortfolioService(dataAccessService);
    }

    @Test
    public void testCreatePortfolio() {
        ResponseEntity<Portfolio> portfolio = createPortfolioService.createPortfolio();
        assertNotNull("Response should not be null.", portfolio);
        assertNotNull("Body should not be null.", portfolio.getBody());
        assertNotNull("Id should not be null.", portfolio.getBody().getId());
        verify(dataAccessService, times(1)).savePortfolio(any(Portfolio.class));
    }
}