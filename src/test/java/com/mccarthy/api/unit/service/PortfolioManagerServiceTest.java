package com.mccarthy.api.unit.service;


import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import com.mccarthy.api.service.PortfolioManagerService;
import com.mccarthy.api.service.dao.DataAccessService;
import com.mccarthy.api.service.external.PriceCheckerService;
import com.mccarthy.api.validation.PortfolioValidationService;
import com.mccarthy.api.validation.SymbolValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PortfolioManagerServiceTest {
    private Portfolio portfolio;

    @Mock
    DataAccessService dataAccessService;
    @Mock
    PortfolioValidationService portfolioValidationService;
    @Mock
    PriceCheckerService priceCheckerService;
    @Mock
    SymbolValidationService symbolValidationService;

    private PortfolioManagerService portfolioManagerService;

    @Before
    public void setup(){
        portfolio = new Portfolio();
        when(dataAccessService.getPortfolio(anyString())).thenReturn(portfolio);
        when(priceCheckerService.getPrice(anyString())).thenReturn(new BigDecimal("100")).thenReturn(new BigDecimal("200"));
        portfolioManagerService = new PortfolioManagerService(dataAccessService, portfolioValidationService, priceCheckerService,symbolValidationService);
    }
    @Test
    public void testCreatePortfolio() {
        ResponseEntity<Portfolio> portfolio = portfolioManagerService.createPortfolio();
        assertNotNull("Response should not be null.", portfolio);
        assertNotNull("Body should not be null.", portfolio.getBody());
        assertNotNull("Id should not be null.", portfolio.getBody().getId());
        verify(dataAccessService, times(1)).savePortfolio(any(Portfolio.class));
    }

    @Test
    public void testGetPortfolioFromCache() {
        ArrayList<Symbol> symbols = new ArrayList<>();
        Symbol tsla = new Symbol();
        tsla.setName("TSLA");
        tsla.setVolume(10);
        symbols.add(tsla);

        Symbol aapl = new Symbol();
        aapl.setName("AAPL");
        aapl.setVolume(5);
        symbols.add(aapl);

        portfolio.setSymbols(symbols);

        when(dataAccessService.getPortfolio(anyString())).thenReturn(portfolio);

        ResponseEntity<Portfolio> responseEntity = portfolioManagerService.getPortfolio("123");
        Portfolio portfolio = responseEntity.getBody();
        System.out.println(portfolio);
        assertEquals(0, new BigDecimal("2000").compareTo(portfolio.getTotalValue()));

        for (Symbol s : portfolio.getSymbols()) {
            if ("AAPL".equals(s.getName())) {
                assertEquals("AAPL stock value is incorrect.", 0, new BigDecimal("1000").compareTo(s.getTotalValue()));
            } else if ("TSLA".equals(s.getName())) {
                assertEquals("TSLA stock value is incorrect.", 0, new BigDecimal("1000").compareTo(s.getTotalValue()));
            } else {
                fail("Unexpected symbol in response");
            }
        }
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
        portfolioManagerService.updatePortfolio("id", input);

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
        portfolioManagerService.updatePortfolio("id", input);

        verify(symbolValidationService, times(1)).validatePortfolioDoesNotContainNewSymbols(anyString(), eq(input));
        verify(symbolValidationService, times(1)).validateSymbolExistsInExternalSystem(eq(input));
        verify(dataAccessService, times(1)).savePortfolio(any(Portfolio.class));
    }


    @Test
    public void testDeletePortfolio() {
        portfolioManagerService.deletePortfolio("123");
        verify(portfolioValidationService, times(1)).validatePortfolioExists(anyString());
        verify(dataAccessService, times(1)).deletePortfolio(anyString());
    }

    @Test
    public void testDeleteSymbolFromPortfolio() {
        portfolioManagerService.deleteSymbol("123", "AAPL");
        verify(portfolioValidationService, times(1)).validatePortfolioExists(anyString());
        verify(symbolValidationService, times(1)).validatePortfolioContainsSymbol(anyString(), anyString());
        verify(dataAccessService, times(1)).deleteSymbol(anyString(), anyString());
    }
}