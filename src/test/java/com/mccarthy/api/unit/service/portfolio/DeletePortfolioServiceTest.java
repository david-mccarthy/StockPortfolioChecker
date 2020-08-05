package com.mccarthy.api.unit.service.portfolio;

import com.mccarthy.api.service.dao.DataAccessService;
import com.mccarthy.api.service.portfolio.DeletePortfolioService;
import com.mccarthy.api.validation.PortfolioValidationService;
import com.mccarthy.api.validation.SymbolValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeletePortfolioServiceTest {
    @Mock
    protected DataAccessService dataAccessService;
    @Mock
    protected PortfolioValidationService portfolioValidationService;
    @Mock
    protected SymbolValidationService symbolValidationService;

    protected DeletePortfolioService deletePortfolioService;

    @Before
    public void setup() {
        deletePortfolioService = new DeletePortfolioService(dataAccessService, portfolioValidationService, symbolValidationService);
    }

    @Test
    public void testDeletePortfolio() {
        deletePortfolioService.deletePortfolio("123");
        verify(portfolioValidationService, times(1)).validatePortfolioExists(anyString());
        verify(dataAccessService, times(1)).deletePortfolio(anyString());
    }

    @Test
    public void testDeleteSymbolFromPortfolio() {
        deletePortfolioService.deleteSymbol("123", "AAPL");
        verify(portfolioValidationService, times(1)).validatePortfolioExists(anyString());
        verify(symbolValidationService, times(1)).validatePortfolioContainsSymbol(anyString(), anyString());
        verify(dataAccessService, times(1)).deleteSymbol(anyString(), anyString());
    }
}