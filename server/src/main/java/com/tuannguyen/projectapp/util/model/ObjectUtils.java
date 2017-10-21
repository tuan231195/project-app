package com.tuannguyen.projectapp.util.model;

import org.springframework.security.access.method.P;

public class ObjectUtils {
    public static <T> T defaultIfNull(T value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
