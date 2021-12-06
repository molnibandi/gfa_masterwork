package com.greenfox.molnibandi_masterwork.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class ErrorResponse {

    private int statusCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp timestamp;
    private String message;
    private String description;

    public static class ErrorResponseBuilder {

        private int statusCode;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        private Timestamp timestamp;
        private String message;
        private String description;

        public ErrorResponseBuilder() {
        }

        public ErrorResponseBuilder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ErrorResponseBuilder timeStamp(Timestamp timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ErrorResponse build() {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.statusCode = this.statusCode;
            errorResponse.timestamp = this.timestamp;
            errorResponse.message = this.message;
            errorResponse.description = this.description;
            return errorResponse;
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

}
