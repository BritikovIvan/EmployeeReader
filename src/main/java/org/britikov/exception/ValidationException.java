package org.britikov.exception;

import lombok.Getter;

@Getter
public class ValidationException extends Exception {
    private final String errorLine;

    public ValidationException(String errorLine) {
        super("Data parsing exception");
        this.errorLine = errorLine;
    }

    public ValidationException(String message, String errorLine) {
        super(message);
        this.errorLine = errorLine;
    }
}
