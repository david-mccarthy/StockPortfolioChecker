package com.mccarthy.api.error;

import com.mccarthy.api.model.ApiError;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Service to handle generation of api errors.
 */
@Service
public class ErrorService {
    private MessageSource errorMessageSource;
    private HttpServletRequest request;

    public ErrorService(MessageSource errorMessageSource, HttpServletRequest request) {
        this.errorMessageSource = errorMessageSource;
        this.request = request;
    }

    /**
     * Create an api error for returning error details to the client.
     *
     * @param exception Thrown exception which caused the error.
     * @param errorCode Error code to return to the client.
     * @return Api error.
     */
    public ApiError createApiError(Exception exception, String errorCode) {
        return createError(errorCode, errorMessageSource.getMessage(errorCode, new Object[]{}, request.getLocale()), exception.getMessage());
    }

    private ApiError createError(String errorCode, String errorMessage, String exceptionMessage) {
        ApiError apiError = new ApiError();
        apiError.setErrorCode(errorCode);
        apiError.setErrorMessage(errorMessage);
        apiError.setExceptionMessage(exceptionMessage);

        return apiError;
    }


    public ApiError createApiError(Exception exception, String errorCode, List<String> args) {
        return createError(errorCode, errorMessageSource.getMessage(errorCode, args.toArray(), request.getLocale()), exception.getMessage());
    }

}
