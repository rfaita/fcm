package com.product.fcm.client.shared.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.product.fcm.shared.dto.ErrorDTO;

import java.util.List;

public class CustomHystrixBadRequestException extends HystrixBadRequestException {

    private final List<ErrorDTO> errors;


    public List<ErrorDTO> getErros() {
        return errors;
    }

    public CustomHystrixBadRequestException(List<ErrorDTO> errors) {
        super(errors.get(0).getErrorMessage());
        this.errors = errors;
    }

    public CustomHystrixBadRequestException(String message, List<ErrorDTO> errors) {
        super(message);
        this.errors = errors;

    }

    public CustomHystrixBadRequestException(String message, Throwable cause, List<ErrorDTO> errors) {
        super(message, cause);
        this.errors = errors;
    }
}
