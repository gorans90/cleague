package com.htec.task.controllers.common;

import com.htec.task.exceptions.GameStatsException;
import com.htec.task.exceptions.StandingException;
import com.htec.task.exceptions.TableStatsException;
import com.htec.task.utils.ErrorCode;
import com.htec.task.utils.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler that maps exceptions to HTTP error status codes 400+ or 500
 */

@Slf4j
@RestControllerAdvice
public class PlatformControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGameStatsException(GameStatsException e) {
        log.info("GameStatsException received, message: {}", e.getMessage());
        return new ErrorResponse(ErrorCode.GAME_STATS_NOT_FOUND, e.toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTableStatsException(TableStatsException e) {
        log.info("TableStatsException received, message: {}", e.getMessage());
        return new ErrorResponse(ErrorCode.TABLE_STATS_NOT_FOUND, e.toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleStandingException(StandingException e) {
        log.info("StandingException received, message: {}", e.getMessage());
        return new ErrorResponse(ErrorCode.STANDINGS_NOT_FOUND, e.toString(), e.getMessage());
    }

}
