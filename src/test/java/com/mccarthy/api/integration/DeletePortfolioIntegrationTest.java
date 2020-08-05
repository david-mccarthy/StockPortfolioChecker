package com.mccarthy.api.integration;

import com.mccarthy.api.model.Portfolio;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeletePortfolioIntegrationTest extends IntegrationTestBase {

    @Test
    public void testDeletePortfolio() {
        ResponseEntity<Portfolio> portfolio = createPortfolio();
        assertEquals(HttpStatus.CREATED, portfolio.getStatusCode());
        String id = portfolio.getBody().getId();
        ResponseEntity<Void> deleteResponse = sendRequest("/portfolio/" + id, HttpMethod.DELETE, null, Void.class);
        assertNotNull(deleteResponse);
        assertEquals("HttpStatus code should be NO CONTENT.", HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }

    @Test
    public void testDeletePortfolioDoesNotExist() {
        ResponseEntity<Portfolio> portfolio = createPortfolio();
        assertEquals(HttpStatus.CREATED, portfolio.getStatusCode());
        String id = portfolio.getBody().getId();
        ResponseEntity<Void> deleteResponse = sendRequest("/portfolio/newId", HttpMethod.DELETE, null, Void.class);
        assertNotNull(deleteResponse);
        assertEquals("HttpStatus code should be NO CONTENT.", HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }
}
