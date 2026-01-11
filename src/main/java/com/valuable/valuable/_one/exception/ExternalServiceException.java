package com.valuable.valuable._one.exception;

import org.springframework.http.HttpStatus;

public class ExternalServiceException extends RuntimeException {

    private final HttpStatus status;

    public ExternalServiceException(String message) {
        super(message);
        this.status = HttpStatus.SERVICE_UNAVAILABLE;
    }

    public HttpStatus getStatus() {
        return status;
    }

}

