package com.mccarthy.api.integration;

import com.mccarthy.api.model.Portfolio;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteSymbolFromPortfolioIntegrationTest extends IntegrationTestBase {
    private String id;
    private static final String SYMBOL = "AAPL";

    @Before
    public void setup() {
        ResponseEntity<Portfolio> portfolio = createPortfolio();
        id = portfolio.getBody().getId();
        addItemToPortfolio(SYMBOL, id);
    }

    @Test
    public void testDeleteSymbolFromPortfolio() {
        ResponseEntity<Void> voidResponseEntity = sendRequest("/portfolio/" + id + "/symbol/" + SYMBOL, HttpMethod.DELETE, null, Void.class);
        assertEquals("HttpStatus code NO CONTENT expected.", HttpStatus.NO_CONTENT, voidResponseEntity.getStatusCode());
    }

    @Test
    public void testDeleteSymbolFromPortfolioDoesNotExist() {
        ResponseEntity<Void> voidResponseEntity = sendRequest("/portfolio/newId/symbol/" + SYMBOL, HttpMethod.DELETE, null, Void.class);
        assertEquals("", HttpStatus.I_AM_A_TEAPOT, voidResponseEntity.getStatusCode());
    }

    @Test
    public void testDeleteSymbolFromPortfolioSymbolDoesNotExist() {
        ResponseEntity<Void> voidResponseEntity = sendRequest("/portfolio/newId/symbol/TSLA", HttpMethod.DELETE, null, Void.class);
        assertEquals("", HttpStatus.I_AM_A_TEAPOT, voidResponseEntity.getStatusCode());
    }
}
