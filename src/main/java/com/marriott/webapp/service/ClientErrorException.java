package com.marriott.webapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientErrorException extends RuntimeException {
    public ClientErrorException(final String message) {
        super(message);
    }
}