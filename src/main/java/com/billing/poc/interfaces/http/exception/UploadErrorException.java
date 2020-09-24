package com.billing.poc.interfaces.http.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UploadErrorException extends RuntimeException{

    public UploadErrorException(String message) {
        super(message);
    }
}
