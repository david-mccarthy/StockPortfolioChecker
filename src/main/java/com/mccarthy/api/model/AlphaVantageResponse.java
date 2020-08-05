package com.mccarthy.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to hold information returned by the AlphaVantage service.
 * This service did not provide it's own model so I am maintaining one here.
 * This is not ideal, as a change to their model breaks the API.
 *
 * We would not be going live against an API we do not have an agreement in place, or a versioned API which should not break without warning.
 */
public class AlphaVantageResponse {
    @JsonProperty("Global Quote")
    protected GlobalQuote globalQuote;

    public GlobalQuote getGlobalQuote() {
        return globalQuote;
    }

    public static class GlobalQuote {
        @JsonProperty("01. symbol")
        protected String symbol;
        @JsonProperty("02. open")
        protected String open;
        @JsonProperty("03. high")
        protected String high;
        @JsonProperty("04. low")
        protected String low;
        @JsonProperty("05. price")
        protected String price;
        @JsonProperty("06. volume")
        protected String volume;
        @JsonProperty("07. latest trading day")
        protected String latestTradingDay;
        @JsonProperty("08. previous close")
        protected String previousClose;
        @JsonProperty("09. change")
        protected String change;
        @JsonProperty("10. change percent")
        protected String changePercent;

        public String getSymbol() {
            return symbol;
        }

        public String getOpen() {
            return open;
        }

        public String getHigh() {
            return high;
        }

        public String getLow() {
            return low;
        }

        public String getPrice() {
            return price;
        }

        public String getVolume() {
            return volume;
        }

        public String getLatestTradingDay() {
            return latestTradingDay;
        }

        public String getPreviousClose() {
            return previousClose;
        }

        public String getChange() {
            return change;
        }

        public String getChangePercent() {
            return changePercent;
        }
    }
}
