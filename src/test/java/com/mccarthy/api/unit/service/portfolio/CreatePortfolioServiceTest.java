package com.mccarthy.api.unit.service.portfolio;

import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.service.portfolio.CreatePortfolioService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class CreatePortfolioServiceTest {
    @Mock
    protected DataAccess dataAccessService;
    protected CreatePortfolioService createPortfolioService;
    @Before
    public void setup(){
createPortfolioService = new CreatePortfolioService(dataAccessService);
    }

    @Test
    public void testCreatePortfolio(){
        fail();
    }
}