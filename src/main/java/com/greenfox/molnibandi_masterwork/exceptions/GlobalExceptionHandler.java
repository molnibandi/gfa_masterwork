package com.greenfox.molnibandi_masterwork.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.greenfox.molnibandi_masterwork.exceptions.types.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundHandler(EntityNotFoundException e,
                                                               WebRequest request) {
        ErrorResponse response = new ErrorResponse.ErrorResponseBuilder()
                .statusCode(e.getStatus().value())
                .timeStamp(Timestamp.valueOf(LocalDateTime.now()))
                .message(e.getMessage())
                .description(request.getDescription(true))
                .build();

        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(
            IllegalArgumentException e, WebRequest request) {

        ErrorResponse response = getBadRequestResponse(e.getMessage(), request);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> dataIntegrityViolationExceptionException(
            DataIntegrityViolationException e, WebRequest request) {

        StringBuilder message = parseNestedException(e);

        ErrorResponse response = getBadRequestResponse(message.toString(), request);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    private ErrorResponse getBadRequestResponse(String message, WebRequest request) {
        return new ErrorResponse.ErrorResponseBuilder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(Timestamp.valueOf(LocalDateTime.now()))
                .message(HttpStatus.BAD_REQUEST + " \"" + message + "\"")
                .description(request.getDescription(true))
                .build();
    }

    @NotNull
    private StringBuilder parseNestedException(DataIntegrityViolationException e) {
        StringBuilder message = new StringBuilder();

        Throwable rootCause = e.getRootCause();
        if (rootCause instanceof InvalidFormatException) {
            String rootCauseMessage = rootCause.getMessage();
            message.append(parseInvalidFormatExceptionMessage(rootCauseMessage));

        } else if (rootCause instanceof DateTimeParseException) {
            DateTimeParseException cause = (DateTimeParseException) rootCause;
            message.append("Given \"").append(cause).append("\" date format is invalid.");
        } else {
            message.append(e.getMessage());
        }
        return message;
    }

    @NotNull
    private StringBuilder parseInvalidFormatExceptionMessage(String rootCauseMessage) {
        int i = rootCauseMessage.indexOf("\n");
        int first = rootCauseMessage.indexOf("`") - 8;
        int last = rootCauseMessage.lastIndexOf("`") + 1;
        StringBuilder builder = new StringBuilder(rootCauseMessage);
        builder.setLength(i);
        builder.delete(first, last);
        return builder;
    }

}
