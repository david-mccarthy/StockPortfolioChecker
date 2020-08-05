package com.mccarthy.api.unit.validation;

import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.service.dao.DataAccess;
import com.mccarthy.api.service.external.PriceChecker;
import com.mccarthy.api.validation.SymbolValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SymbolValidationServiceTest {
    @Mock
    protected DataAccess dataAccessService;
    @Mock
    protected PriceChecker priceCheckService;

    protected SymbolValidationService symbolValidationService;

    @Before
    public void setup() {
        symbolValidationService = new SymbolValidationService(dataAccessService, priceCheckService);
    }

    @Test
    public void testValidatePortfolioDoesNotContainNewSymbols() {
        when(dataAccessService.getPortfolio(anyString())).thenReturn(new Portfolio());
        symbolValidationService.validatePortfolioDoesNotContainNewSymbols("", new AddItemInput());
    }

    @Test
    public void testValidatePortfolioContainsSymbol() {
        symbolValidationService.validatePortfolioContainsSymbol("", "");
    }

    @Test
    public void validateSymbolValid() {
        symbolValidationService.validateSymbolExistsInExternalSystem(new AddItemInput());
    }
}