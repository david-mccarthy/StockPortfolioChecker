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

/**
 * Service to connect to the AlphaVantage REST api for stock price information.
 */
@Service
public class PriceCheckerServiceAlphaVantageImpl implements PriceCheckerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceCheckerServiceAlphaVantageImpl.class);
    protected final ConfigService configService;

    public PriceCheckerServiceAlphaVantageImpl(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getPrice(String symbol) {
        LOGGER.info("Getting price for symbol " + symbol + " from supplier.");
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidSymbol(String symbol) {
        LOGGER.info("Checking if symbol " + symbol + " is valid against our supplier.");
        String url = getUrl(symbol);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AlphaVantageResponse> response = restTemplate.getForEntity(url, AlphaVantageResponse.class);

        AlphaVantageResponse body = response.getBody();
        return body != null && body.getGlobalQuote() != null && body.getGlobalQuote().getSymbol() != null
                && !"".equals(body.getGlobalQuote().getSymbol());
    }

    /**
     * Build the url for our service call.
     *
     * @param symbol Symbol to include in the url/
     * @return URL.
     */
    protected String getUrl(String symbol) {
        String alphaVantageUrl = configService.getAlphaVantageUrl();
        String alphaVantageApiKey = configService.getAlphaVantageApiKey();

        return String.format(alphaVantageUrl, symbol, alphaVantageApiKey);
    }
}
