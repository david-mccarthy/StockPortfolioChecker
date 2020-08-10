
# Stock portfolio checker
Spring boot REST API to build a stock portfolio and check it's value.

## Usage
**Important!**
This application calls out to an external web service to retrieve stock information. For the application to function correctly, you must first get a (free) key from AlphaVantage: [https://www.alphavantage.co/support/#api-key](https://www.alphavantage.co/support/#api-key)

This key should be added to the application.properties of the project:

    #AlphaVantage api details:  
    alphavantage.url=https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s  
    alphavantage.apikey=ABCDEFG

## Running the application:

This is a gradle project. To start the application use the following command from the project root:
 

       gradlew bootRun

The application is now running on http://localhost:8080
**Create a new portfolio** by sending a POST request to: http://localhost:8080/portfolio
This will return a portfolio with an id. Use this id for the other operations:

**Retrieve a portfolio:**
GET: http://localhost:8080/portfolio/{portfolioId}

**Add symbols to a portfolio:**
POST: http://localhost:8080/portfolio/{portfolioId}
Request body:

    {
    	"symbols":[
    		{
	    		"name": "AAPL",
    			"volume":100
    		},
    		{
	    		"name": "TSLA",
    			"volume":25
    		}
    	]
    }
**Delete a symbol from your portfolio:**
DELETE: http://localhost:8080/portfolio/{portfolioId}/symbol/{symbol}

**Delete the portfolio:**
DELETE: http://localhost:8080/portfolio/{portfolioId}/

## Testing

To run the tests, use the gradle wrapper from the project root:

    gradlew test

Tests consist of:
 - Integration tests
  Spring integration tests using wiremock to stub  the responses from the external service. 
  - Unit tests: 
  Junit tests using mockito to stub dependency method calls

