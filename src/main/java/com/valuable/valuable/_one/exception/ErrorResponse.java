package com.valuable.valuable._one.exception;


import java.time.LocalDateTime;


public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
    private String timestamp;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now().toString();
    }


    public int getStatus() {
        return status;
    }

    public ErrorResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getError() {
        return error;
    }

    public ErrorResponse setError(String error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ErrorResponse setPath(String path) {
        this.path = path;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ErrorResponse setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}

