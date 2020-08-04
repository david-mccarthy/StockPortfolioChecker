package com.mccarthy.api.service.external;

import com.mccarthy.api.error.exceptions.ExternalServiceException;
import com.mccarthy.api.model.AlphaVantageResponse;
import com.mccarthy.api.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class AlphaVantageService implements PriceChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlphaVantageService.class);
    protected final ConfigService configService;

    public AlphaVantageService(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public BigDecimal getPrice(String symbol) {
        String url = getUrl(symbol);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AlphaVantageResponse> response = restTemplate.getForEntity(url, AlphaVantageResponse.class);

        AlphaVantageResponse body = response.getBody();
        AlphaVantageResponse.GlobalQuote globalQuote = body.getGlobalQuote();

        String price = globalQuote.getPrice();
        if (price == null || "".equals(price)) {
            LOGGER.debug("Could not get the price for symbol " + symbol + " from external service");
            throw new ExternalServiceException("Could not get a price for symbol " + symbol);
        }
        return new BigDecimal(price);
    }

    protected String getUrl(String symbol) {
        String alphaVantageUrl = configService.getAlphaVantageUrl();
        String alphaVantageApiKey = configService.getAlphaVantageApiKey();

        return String.format(alphaVantageUrl, symbol, alphaVantageApiKey);
    }
}
