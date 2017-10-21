package com.tuannguyen.projectapp.core.exception;

public class ApiException extends Throwable {
    private String title;
    private String message;

    public ApiException(String title, String message) {
        super(title);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }
}
