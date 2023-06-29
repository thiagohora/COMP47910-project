package com.marriott.webapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SystemConflictException extends RuntimeException {

    public SystemConflictException(final String message) {
        super(message);
    }
}
