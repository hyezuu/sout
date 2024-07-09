package com.sout.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member Not Found");

    private final int status;
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
