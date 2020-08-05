package com.mccarthy.api.integration;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.mccarthy.api.model.AddItemInput;
import com.mccarthy.api.model.Portfolio;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@RunWith(SpringRunner.class)
@Ignore
public class IntegrationTestBase {

    @Autowired
    TestRestTemplate restTemplate;

    @Rule
    public WireMockRule wiremock = new WireMockRule(options()
            .port(4081)
            .usingFilesUnderDirectory("src/test/data/wiremock"));

    @Before
    public void setupWiremockResponses() throws IOException {
        setupStub("/query?function=GLOBAL_QUOTE&symbol=AAPL&apikey=TEST", "src/test/data/wiremock/AAPL.json");
        setupStub("/query?function=GLOBAL_QUOTE&symbol=TSLA&apikey=TEST", "src/test/data/wiremock/TSLA.json");
    }

    protected <T> ResponseEntity<T> sendRequest(String url, HttpMethod httpMethod, Object requestObject, Class<T> responseType) {
        HttpEntity<?> req = new HttpEntity(requestObject);
        return restTemplate.exchange(url, httpMethod, req, responseType);
    }

    public ResponseEntity<Portfolio> createPortfolio() {
        return sendRequest("/portfolio", HttpMethod.POST, null, Portfolio.class);
    }

    public void setupStub(String url, String responseFile) throws IOException {
        stubFor(get(urlEqualTo(url))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(getFileContent(responseFile))));
    }

    protected String getFileContent(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));

        return new String(encoded, StandardCharsets.UTF_8);
    }

    public ResponseEntity<Void> addItemToPortfolio(String aapl, String id) {
        AddItemInput input = new AddItemInput();
        ArrayList<AddItemInput.SymbolInput> symbols = new ArrayList<>();
        AddItemInput.SymbolInput symbol = new AddItemInput.SymbolInput();
        symbol.setName(aapl);
        symbol.setVolume(100);
        symbols.add(symbol);
        input.setSymbols(symbols);
        return sendRequest("/portfolio/" + id + "/", HttpMethod.POST, input, Void.class);
    }
}
