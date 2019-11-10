package com.htec.task.configs;

import com.htec.task.utils.ErrorResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for response codes
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Operation completed successfully"),
        @ApiResponse(code = 400, response = ErrorResponse.class, message = "Bad request, invalid input parameters. Error code: BAD_REQUEST"),
        @ApiResponse(code = 401, response = ErrorResponse.class, message = "Invalid credentials provided. Error code: UNAUTHORIZED"),
        @ApiResponse(code = 403, response = ErrorResponse.class, message = "Operation not permitted. Error code: FORBIDDEN"),
        @ApiResponse(code = 500, response = ErrorResponse.class, message = "Runtime server error occurred. Error code: SERVER_ERROR")})
public @interface ApiResponseCodes {
}
