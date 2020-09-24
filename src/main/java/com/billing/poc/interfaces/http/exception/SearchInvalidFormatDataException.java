package com.billing.poc.interfaces.http.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SearchInvalidFormatDataException extends RuntimeException{

	public SearchInvalidFormatDataException() {
        super();
    }
    public SearchInvalidFormatDataException(String message, Throwable cause) {
        super(message, cause);
    }
    public SearchInvalidFormatDataException(String message) {
        super(message);
    }
    public SearchInvalidFormatDataException(Throwable cause) {
        super(cause);
    }
}
