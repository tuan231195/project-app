package com.tuannguyen.projectapp.core.json;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private String title;
    private List<String> errorList;
    private String error;

    public Error(String title) {
        this.title = title;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public Error errors(List<String> errorList) {
        this.errorList = errorList;
        return this;
    }

    public String getError() {
        return error;
    }

    public Error error(String error) {
        this.error = error;
        return this;
    }
}
