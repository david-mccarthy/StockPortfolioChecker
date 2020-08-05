package com.mccarthy.api.unit.service.portfolio;

import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import com.mccarthy.api.service.dao.DataAccessService;
import com.mccarthy.api.service.external.PriceCheckerService;
import com.mccarthy.api.service.portfolio.GetPortfolioService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetPortfolioServiceTest {
    public Portfolio portfolio;
    @Mock
    protected DataAccessService dataAccessService;
    @Mock
    protected PriceCheckerService priceCheckerService;
    protected GetPortfolioService getPortfolioService;

    @Before
    public void setup() {
        portfolio = new Portfolio();
        when(priceCheckerService.getPrice(anyString())).thenReturn(new BigDecimal("100")).thenReturn(new BigDecimal("200"));
        getPortfolioService = new GetPortfolioService(dataAccessService, priceCheckerService);
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

        ResponseEntity<Portfolio> responseEntity = getPortfolioService.getPortfolio("123");
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
}