package com.mccarthy.api.integration;

import com.mccarthy.api.model.Portfolio;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class CreatePortfolioIntegrationTest extends IntegrationTestBase {

    @Test
    public void testCreatePortfolio() {
        ResponseEntity<Portfolio> portfolio = createPortfolio();
        Portfolio body = portfolio.getBody();
        assertNotNull("Body should not be null.", body);
        assertNotNull("Id should not be null.", body.getId());
        assertEquals("HttpStatus should be CREATED.", HttpStatus.CREATED, portfolio.getStatusCode());
    }
}
