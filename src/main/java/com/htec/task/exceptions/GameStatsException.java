package com.htec.task.exceptions;

/**
 * Exception for {@link com.htec.task.models.GameStats}
 */

public class GameStatsException extends Exception {

    private String message;
    private int code;

    public GameStatsException(String message, int code) {
        super(message);
        this.code = code;
    }
}
