package com.mccarthy.api.unit.validation;

import com.mccarthy.api.error.exceptions.DuplicateSymbolValidationException;
import com.mccarthy.api.error.exceptions.InvalidSymbolException;
import com.mccarthy.api.error.exceptions.SymbolException;
import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import com.mccarthy.api.service.dao.DataAccessService;
import com.mccarthy.api.service.external.PriceCheckerService;
import com.mccarthy.api.validation.SymbolValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SymbolValidationServiceTest {
    protected Portfolio portfolio;
    @Mock
    protected DataAccessService dataAccessService;
    @Mock
    protected PriceCheckerService priceCheckService;

    protected SymbolValidationService symbolValidationService;

    @Before
    public void setup() {
        portfolio = new Portfolio();
        when(dataAccessService.getPortfolio(anyString())).thenReturn(portfolio);
        symbolValidationService = new SymbolValidationService(dataAccessService, priceCheckService);
    }

    @Test
    public void testValidatePortfolioDoesNotContainNewSymbols_NoDuplicate() {
        ArrayList<Symbol> portfolioSymbols = new ArrayList<>();
        Symbol portfolioSymbol = new Symbol();
        portfolioSymbol.setName("AAPL");
        portfolioSymbols.add(portfolioSymbol);
        portfolio.setSymbols(portfolioSymbols);

        AddItemInput input = new AddItemInput();
        ArrayList<AddItemInput.SymbolInput> symbols = new ArrayList<>();
        AddItemInput.SymbolInput symbol = new AddItemInput.SymbolInput();
        symbol.setName("TSLA");
        symbols.add(symbol);
        input.setSymbols(symbols);
        boolean exceptionThrown = false;
        try{
            symbolValidationService.validatePortfolioDoesNotContainNewSymbols("123", input);
        } catch(DuplicateSymbolValidationException e){
            exceptionThrown = true;
        }

        assertFalse(exceptionThrown);
    }

    @Test
    public void testValidatePortfolioDoesNotContainNewSymbols_Duplicate() {
        ArrayList<Symbol> portfolioSymbols = new ArrayList<>();
        Symbol portfolioSymbol = new Symbol();
        portfolioSymbol.setName("TSLA");
        portfolioSymbols.add(portfolioSymbol);
        portfolio.setSymbols(portfolioSymbols);

        AddItemInput input = new AddItemInput();
        ArrayList<AddItemInput.SymbolInput> symbols = new ArrayList<>();
        AddItemInput.SymbolInput symbol = new AddItemInput.SymbolInput();
        symbol.setName("TSLA");
        symbols.add(symbol);
        input.setSymbols(symbols);
        boolean exceptionThrown = false;
        try{
            symbolValidationService.validatePortfolioDoesNotContainNewSymbols("123", input);
        } catch(DuplicateSymbolValidationException e){
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    @Test
    public void testValidatePortfolioContainsSymbol() {
        ArrayList<Symbol> symbols = new ArrayList<>();
        Symbol symbol = new Symbol();
        symbol.setName("AAPL");
        symbols.add(symbol);
        portfolio.setSymbols(symbols);
        boolean exceptionThrown = false;
        try {
            symbolValidationService.validatePortfolioContainsSymbol("123", "AAPL");
        } catch (SymbolException e) {
            exceptionThrown = true;
        }

        assertFalse("Exception should not have been thrown.", exceptionThrown);
    }

    @Test
    public void testValidatePortfolioContainsSymbolEmpySymbolList() {
        portfolio.setSymbols(new ArrayList<>());
        boolean exceptionThrown = false;
        try {
            symbolValidationService.validatePortfolioContainsSymbol("123", "AAPL");
        } catch (SymbolException e) {
            exceptionThrown = true;
        }

        assertTrue("Exception should have been thrown.", exceptionThrown);
    }

    @Test
    public void testValidatePortfolioContainsSymbol_NotPresent() {
        ArrayList<Symbol> symbols = new ArrayList<>();
        Symbol symbol = new Symbol();
        symbol.setName("AAPL");
        symbols.add(symbol);
        portfolio.setSymbols(symbols);
        boolean exceptionThrown = false;
        try {
            symbolValidationService.validatePortfolioContainsSymbol("123", "TSLA");
        } catch (SymbolException e) {
            exceptionThrown = true;
        }

        assertTrue("Exception should have been thrown.", exceptionThrown);
    }

    @Test
    public void validateSymbolValid() {
        when(priceCheckService.isValidSymbol(anyString())).thenReturn(true);
        AddItemInput input = new AddItemInput();
        ArrayList<AddItemInput.SymbolInput> symbols = new ArrayList<>();
        AddItemInput.SymbolInput symbol = new AddItemInput.SymbolInput();
        symbol.setName("AAPL");
        symbols.add(symbol);
        input.setSymbols(symbols);
        boolean exceptionThrown = false;
        try {
            symbolValidationService.validateSymbolExistsInExternalSystem(input);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        assertFalse("Exception should not have been thrown.", exceptionThrown);
    }

    @Test
    public void validateSymbolValid_NotValid() {
        when(priceCheckService.isValidSymbol(anyString())).thenReturn(false);
        AddItemInput input = new AddItemInput();
        ArrayList<AddItemInput.SymbolInput> symbols = new ArrayList<>();
        AddItemInput.SymbolInput symbol = new AddItemInput.SymbolInput();
        symbol.setName("APPLE");
        symbols.add(symbol);
        input.setSymbols(symbols);
        boolean exceptionThrown = false;
        try {
            symbolValidationService.validateSymbolExistsInExternalSystem(input);
        } catch (InvalidSymbolException e) {
            exceptionThrown = true;
        }

        assertTrue("Exception should not have been thrown.", exceptionThrown);
    }
}