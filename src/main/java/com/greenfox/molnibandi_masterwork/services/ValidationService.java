package com.greenfox.molnibandi_masterwork.services;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationService {

    public static void requireNull(Object o, String message) {
        if (o != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireNonNull(Object o, String message) {
        if (o == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static Long isNumeric(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Id must be a number.");
        }
    }

    public static void checkBinding(BindingResult bindingResult) {
        if (bindingResult != null) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            if (!errors.isEmpty()) {
                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("Invalid parameters were found: ");
                for (ObjectError error : errors) {
                    messageBuilder.append(error.getDefaultMessage());
                }
                throw new IllegalArgumentException(messageBuilder.toString());
            }
        }
    }

}
