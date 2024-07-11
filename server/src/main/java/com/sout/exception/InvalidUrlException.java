package com.sout.exception;

public class InvalidUrlException extends IllegalArgumentException {
    public InvalidUrlException(String message) {
        super(message);
    }
}
