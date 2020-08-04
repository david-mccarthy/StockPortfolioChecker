package com.mccarthy.api.integration;

import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetPortfolioIntegrationTest extends IntegrationTestBase {
    private String id;
    private static final String AAPL = "AAPL";
    private static final String TSLA = "TSLA";

    @Before
    public void setup() {
        ResponseEntity<Portfolio> portfolio = createPortfolio();
        id = portfolio.getBody().getId();
        addItemToPortfolio(AAPL, id);
        addItemToPortfolio(TSLA, id);
    }

    @Test
    public void testGetPortfolio() {
        ResponseEntity<Portfolio> portfolioResponse = sendRequest("/portfolio/" + id, HttpMethod.GET, null, Portfolio.class);
        assertEquals(HttpStatus.OK, portfolioResponse.getStatusCode());
        Portfolio portfolio = portfolioResponse.getBody();
        assertNotNull("Portfolio should not be null.", portfolio);
        assertNotNull("Portfolio id should not be null.", portfolio.getId());
        List<Symbol> symbols = portfolio.getSymbols();
        assertEquals("There should be 2 symbols returned in the portfolio.", 2, symbols.size());
        for (Symbol symbol : symbols) {
            assertNotNull("Symbols name should no be null.", symbol.getSymbol());
            assertNotNull("Symbols price should not be null.", symbol.getPrice());
            assertNotNull("Symbols volume should not be null.", symbol.getVolume());
        }
        assertNotNull("The value of the portfolio should not be null.", portfolio.getTotalValue());
    }
}
