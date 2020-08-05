package com.mccarthy.api.unit.validation;

import com.mccarthy.api.error.exceptions.NoPortfolioException;
import com.mccarthy.api.service.dao.DataAccessService;
import com.mccarthy.api.validation.PortfolioValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PortfolioValidationServiceTest {
    @Mock
    protected DataAccessService dataAccessService;
    protected PortfolioValidationService portfolioValidationService;

    @Before
    public void setup() {
        portfolioValidationService = new PortfolioValidationService(dataAccessService);
    }

    @Test
    public void testValidationWherePortfolioExists() {
        when(dataAccessService.hasPortfolio(anyString())).thenReturn(true);
        boolean exceptionThrown = false;
        try {
            portfolioValidationService.validatePortfolioExists("id");
        } catch (Exception e) {
            exceptionThrown = true;
        }

        assertFalse("Exception should not have been thrown.", exceptionThrown);
    }

    @Test
    public void testValidationWherePortfolioDoesNotExist() {
        when(dataAccessService.hasPortfolio(anyString())).thenReturn(false);
        boolean exceptionThrown = false;
        try {
            portfolioValidationService.validatePortfolioExists("id");
        } catch (NoPortfolioException e) {
            exceptionThrown = true;
        }

        assertTrue("Exception should have been thrown.", exceptionThrown);
    }
}