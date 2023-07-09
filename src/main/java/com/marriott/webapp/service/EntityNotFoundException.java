package com.marriott.webapp.service;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Resource not found", code = org.springframework.http.HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
}
