package com.htec.task.exceptions;

/**
 * Exception for {@link com.htec.task.models.TableStats}
 */

public class TableStatsException extends Exception {

    private String message;
    private int code;

    public TableStatsException(String message, int code) {
        super(message);
        this.code = code;
    }
}
