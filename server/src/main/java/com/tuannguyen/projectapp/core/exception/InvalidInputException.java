package com.tuannguyen.projectapp.core.exception;

import java.util.List;

public class InvalidInputException extends Exception {
    private List<String> errors;

    public InvalidInputException(String title, List<String> errors) {
        super(title);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
