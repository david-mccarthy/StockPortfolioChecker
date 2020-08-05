package com.mccarthy.api.unit.service.portfolio;

import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.service.portfolio.DeletePortfolioService;
import com.mccarthy.api.validation.PortfolioValidationService;
import com.mccarthy.api.validation.SymbolValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class DeletePortfolioServiceTest {
    @Mock
    protected DataAccess dataAccessService;
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
    public void testDeletePortfolio(){

    }

    @Test
    public void testDeleteSymbolFromPortfolio(){

    }
}