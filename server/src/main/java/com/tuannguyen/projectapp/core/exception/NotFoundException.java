package com.tuannguyen.projectapp.core.exception;

public class NotFoundException extends Exception {
    private String table;
    private String field;
    private String value;

    public NotFoundException(String table, String field, String value) {
        this.table = table;
        this.field = field;
        this.value = value;
    }

    public String getTable() {
        return table;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}
