package com.mccarthy.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    @Value("${alphavantage.apikey}")
    protected String alphaVantageApiKey;
    @Value("${alphavantage.url}")
    protected String alphaVantageUrl;

    public String getAlphaVantageApiKey() {
        return alphaVantageApiKey;
    }

    public void setAlphaVantageApiKey(String alphaVantageApiKey) {
        this.alphaVantageApiKey = alphaVantageApiKey;
    }

    public String getAlphaVantageUrl() {
        return alphaVantageUrl;
    }

    public void setAlphaVantageUrl(String alphaVantageUrl) {
        this.alphaVantageUrl = alphaVantageUrl;
    }

}
