package org.britikov.exception;

public class DataParsingException extends RuntimeException {
    private final String errorLine;

    public DataParsingException(String errorLine) {
        super("Data parsing exception");
    }

    public DataParsingException(String message, String errorLine) {
        super(message);
    }
}
