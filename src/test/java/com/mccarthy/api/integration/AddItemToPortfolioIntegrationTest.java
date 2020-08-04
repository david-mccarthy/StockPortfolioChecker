package com.mccarthy.api.integration;

import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddItemToPortfolioIntegrationTest extends IntegrationTestBase {
    private String id;

    @Before
    public void setup() {
        ResponseEntity<Portfolio> portfolio = createPortfolio();
        id = portfolio.getBody().getId();
    }

    @Test
    public void testAddSingleItem() {
        ResponseEntity<Void> response = addItemToPortfolio("AAPL", id);
        assertEquals("Expected status code OK.", HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddMultipleItems() {
        AddItemInput input = new AddItemInput();
        ArrayList<AddItemInput.SymbolInput> symbols = new ArrayList<>();
        AddItemInput.SymbolInput apple = new AddItemInput.SymbolInput();
        apple.setName("AAPL");
        apple.setVolume(100);
        symbols.add(apple);

        AddItemInput.SymbolInput tesla = new AddItemInput.SymbolInput();
        tesla.setName("TSLA");
        tesla.setVolume(100);
        symbols.add(tesla);

        input.setSymbols(symbols);
        ResponseEntity<Void> response = sendRequest("/portfolio/" + id + "/", HttpMethod.POST, input, Void.class);
        assertEquals("Expected status code OK.", HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddInvalidItem() {
        ResponseEntity<Void> response = addItemToPortfolio("XYZABC", id);
        assertEquals("Expected status code BAD REQUEST.", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
