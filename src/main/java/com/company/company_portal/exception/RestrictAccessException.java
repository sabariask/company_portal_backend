package com.company.company_portal.exception;

public class RestrictAccessException extends RuntimeException {
    public RestrictAccessException(String message) {
        super(message);
    }
}
