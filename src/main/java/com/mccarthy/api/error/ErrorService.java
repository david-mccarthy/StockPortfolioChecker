package com.mccarthy.api.error;

import com.mccarthy.api.model.ApiError;
import org.springframework.stereotype.Service;

@Service
public class ErrorService {

    public ApiError createApiError(Exception e, String errorCode, String errorMessage) {
        ApiError apiError = new ApiError();
        apiError.setErrorCode(errorCode);
        apiError.setErrorMessage(errorMessage);
        apiError.setExceptionMessage(e.getMessage());

        return apiError;
    }
}
