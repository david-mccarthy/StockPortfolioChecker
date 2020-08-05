package com.mccarthy.api.error;

import com.mccarthy.api.model.ApiError;
import org.springframework.stereotype.Service;

/**
 * Service to handle generation of api errors.
 */
@Service
public class ErrorService {
    /**
     * Create an api error for returning error details to the client.
     *
     * @param exception    Thrown exception which caused the error.
     * @param errorCode    Error code to return to the client.
     * @param errorMessage Error message to return to the client.
     * @return Api error.
     */
    public ApiError createApiError(Exception exception, String errorCode, String errorMessage) {
        ApiError apiError = new ApiError();
        apiError.setErrorCode(errorCode);
        apiError.setErrorMessage(errorMessage);
        apiError.setExceptionMessage(exception.getMessage());

        return apiError;
    }
}
