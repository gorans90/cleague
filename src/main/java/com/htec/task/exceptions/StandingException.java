package com.htec.task.exceptions;

/**
 * Exception for {@link com.htec.task.models.Standing}
 */

public class StandingException extends Exception {

    private String message;
    private int code;

    public StandingException(String message, int code) {
        super(message);
        this.code = code;
    }
}
