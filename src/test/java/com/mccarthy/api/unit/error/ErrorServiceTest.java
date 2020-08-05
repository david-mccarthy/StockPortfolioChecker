package com.mccarthy.api.unit.error;

import com.mccarthy.api.error.ErrorService;
import com.mccarthy.api.model.ApiError;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ErrorServiceTest {

    protected ErrorService errorService;

    @Before
    public void setup() {
        errorService = new ErrorService();
    }

    @Test
    public void testCreateError() {
        String exceptionMessage = "Exception to test";
        String errorCode = "Error Code";
        String errorMessage = "Message";
        Exception e = new Exception(exceptionMessage);
        ApiError apiError = errorService.createApiError(e, errorCode, errorMessage);

        assertEquals("Error code incorrect.", errorCode, apiError.getErrorCode());
        assertEquals("Error message incorrect.", errorMessage, apiError.getErrorMessage());
        assertEquals("Exception message incorrect.", exceptionMessage, apiError.getExceptionMessage());
    }
}