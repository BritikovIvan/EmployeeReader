package org.britikov.exception;

public class DataParsingException extends RuntimeException {
    public DataParsingException() {
        super("Data parsing exception");
    }

    public DataParsingException(String message) {
        super(message);
    }

    public DataParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
