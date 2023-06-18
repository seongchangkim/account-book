package com.account.accountbook.user.exception;

public class NotFoundMemberInfoException extends RuntimeException {

    public NotFoundMemberInfoException() {
    }

    public NotFoundMemberInfoException(String message) {
        super(message);
    }

    public NotFoundMemberInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundMemberInfoException(Throwable cause) {
        super(cause);
    }
}
