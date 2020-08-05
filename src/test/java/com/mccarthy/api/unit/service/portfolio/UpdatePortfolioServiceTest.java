package com.mccarthy.api.unit.service.portfolio;

import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.service.dao.DataAccessService;
import com.mccarthy.api.service.portfolio.UpdatePortfolioService;
import com.mccarthy.api.validation.SymbolValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdatePortfolioServiceTest {
    private Portfolio portfolio;
    @Mock
    protected DataAccessService dataAccessService;
    @Mock
    protected SymbolValidationService symbolValidationService;

    protected UpdatePortfolioService updatePortfolioService;

    @Before
    public void setup() {
        portfolio = new Portfolio();
        when(dataAccessService.getPortfolio(anyString())).thenReturn(portfolio);
        updatePortfolioService = new UpdatePortfolioService(dataAccessService, symbolValidationService);
    }

    @Test
    public void testUpdatePortfolioSingleItem() {
        AddItemInput input = new AddItemInput();
        ArrayList<AddItemInput.SymbolInput> symbols = new ArrayList<>();
        AddItemInput.SymbolInput symbol = new AddItemInput.SymbolInput();
        symbol.setName("TESTSYMBOL");
        symbol.setVolume(1000);
        symbols.add(symbol);
        input.setSymbols(symbols);
        updatePortfolioService.updatePortfolio("id", input);

        verify(symbolValidationService, times(1)).validatePortfolioDoesNotContainNewSymbols(anyString(), eq(input));
        verify(symbolValidationService, times(1)).validateSymbolExistsInExternalSystem(eq(input));
        verify(dataAccessService, times(1)).savePortfolio(any(Portfolio.class));
    }


    @Test
    public void testUpdatePortfolioMultipleItems() {
        AddItemInput input = new AddItemInput();
        ArrayList<AddItemInput.SymbolInput> symbols = new ArrayList<>();
        AddItemInput.SymbolInput symbol1 = new AddItemInput.SymbolInput();
        symbol1.setName("TESTSYMBOL1");
        symbol1.setVolume(1000);
        symbols.add(symbol1);

        AddItemInput.SymbolInput symbol2 = new AddItemInput.SymbolInput();
        symbol2.setName("TESTSYMBOL2");
        symbol2.setVolume(1000);
        symbols.add(symbol2);
        input.setSymbols(symbols);
        updatePortfolioService.updatePortfolio("id", input);

        verify(symbolValidationService, times(1)).validatePortfolioDoesNotContainNewSymbols(anyString(), eq(input));
        verify(symbolValidationService, times(1)).validateSymbolExistsInExternalSystem(eq(input));
        verify(dataAccessService, times(1)).savePortfolio(any(Portfolio.class));
    }
}