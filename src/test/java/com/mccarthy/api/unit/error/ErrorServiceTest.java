package com.mccarthy.api.unit.error;

import com.mccarthy.api.error.ErrorService;
import com.mccarthy.api.model.ApiError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ErrorServiceTest {

    protected ErrorService errorService;

    @Mock
    private MessageSource messageSource;
    @Mock

    private HttpServletRequest request;

    @Before
    public void setup() {
        errorService = new ErrorService(messageSource, request);
    }

    @Test
    public void testCreateError() {
        String exceptionMessage = "Exception to test";
        String errorCode = "001";
        String errorMessage = "Message";
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(messageSource.getMessage(eq(errorCode), any(), any(Locale.class))).thenReturn(errorMessage);
        Exception e = new Exception(exceptionMessage);
        ApiError apiError = errorService.createApiError(e, errorCode);

        assertEquals("Error code incorrect.", errorCode, apiError.getErrorCode());
        assertEquals("Error message incorrect.", errorMessage, apiError.getErrorMessage());
        assertEquals("Exception message incorrect.", exceptionMessage, apiError.getExceptionMessage());
    }
}