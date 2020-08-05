package com.mccarthy.api.unit.service.portfolio;

import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.service.external.PriceChecker;
import com.mccarthy.api.service.portfolio.GetPortfolioService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
class GetPortfolioServiceTest {
    @Mock
    protected DataAccess dataAccessService;
    @Mock
    protected PriceChecker priceCheckerService;
    protected GetPortfolioService getPortfolioService;

    @Before
    public void setup() {
        getPortfolioService = new GetPortfolioService(dataAccessService, priceCheckerService);
    }

    @Test
    public void testGetPortfolioFromCache() {
        fail();
    }

}