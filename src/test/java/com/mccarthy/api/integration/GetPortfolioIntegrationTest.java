package com.mccarthy.api.integration;

import com.mccarthy.api.model.Portfolio;
import com.mccarthy.api.model.Symbol;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class GetPortfolioIntegrationTest extends IntegrationTestBase {
    private static final String AAPL = "AAPL";
    private static final String TSLA = "TSLA";

    @Test
    @Ignore("Need to investigate why this test fails when run with the others.")
    public void testGetPortfolio() {
        ResponseEntity<Portfolio> portfolio = createPortfolio();
        String id = portfolio.getBody().getId();
        addItemToPortfolio(AAPL, id);
        addItemToPortfolio(TSLA, id);
        ResponseEntity<Portfolio> portfolioResponse = sendRequest("/portfolio/" + id, HttpMethod.GET, null, Portfolio.class);
        assertEquals(HttpStatus.OK, portfolioResponse.getStatusCode());
        Portfolio portfolioBody = portfolioResponse.getBody();
        assertNotNull("Portfolio should not be null.", portfolioBody);
        assertNotNull("Portfolio id should not be null.", portfolioBody.getId());
        List<Symbol> symbols = portfolioBody.getSymbols();
        assertEquals("There should be 2 symbols returned in the portfolio.", 2, symbols.size());
        for (Symbol symbol : symbols) {
            assertNotNull("Symbols name should no be null.", symbol.getName());
            assertNotNull("Symbols price should not be null.", symbol.getPrice());
            assertNotNull("Symbols volume should not be null.", symbol.getVolume());
        }
        assertNotNull("The value of the portfolio should not be null.", portfolioBody.getTotalValue());
    }

    @Test
    public void testGetPortfolio_DoesNotExist(){
        ResponseEntity<Portfolio> portfolioResponse = sendRequest("/portfolio/testId", HttpMethod.GET, null, Portfolio.class);
        assertEquals("Response should be NOT FOUND.", HttpStatus.NOT_FOUND, portfolioResponse.getStatusCode());
    }
}
