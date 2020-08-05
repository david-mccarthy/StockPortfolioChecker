package com.mccarthy.api.unit.service.portfolio;

import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.service.portfolio.UpdatePortfolioService;
import com.mccarthy.api.validation.SymbolValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
class UpdatePortfolioServiceTest {
    @Mock
    protected DataAccess dataAccessService;
    @Mock
    protected SymbolValidationService symbolValidationService;

    protected UpdatePortfolioService updatePortfolioService;

    @Before
    public void setup() {
        updatePortfolioService = new UpdatePortfolioService(dataAccessService, symbolValidationService);
    }

    @Test
    public void testUpdatePortfolioSingleItem(){
        fail();
    }

    @Test
    public void testUpdatePortfolioMultipleItems(){
        fail();
    }
}