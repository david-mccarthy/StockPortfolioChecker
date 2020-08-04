package com.mccarthy.api.integration;

import com.mccarthy.api.model.Portfolio;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeletePortfolioIntegrationTest extends IntegrationTestBase {
    private String id;

    @Before
    public void setup() {
        ResponseEntity<Portfolio> portfolio = createPortfolio();
        assertEquals(HttpStatus.CREATED, portfolio.getStatusCode());
        id = portfolio.getBody().getId();
    }

    @Test
    public void testDeletePortfolio() {
        ResponseEntity<Void> deleteResponse = sendRequest("/portfolio/" + id, HttpMethod.DELETE, null, Void.class);
        assertNotNull(deleteResponse);
        assertEquals("HttpStatus code should be NO CONTENT.", HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }

    @Test
    public void testDeletePortfolioDoesNotExist() {
        ResponseEntity<Void> deleteResponse = sendRequest("/portfolio/newId", HttpMethod.DELETE, null, Void.class);
        assertNotNull(deleteResponse);
        assertEquals("HttpStatus code should be NO CONTENT.", HttpStatus.I_AM_A_TEAPOT, deleteResponse.getStatusCode());
    }
}
