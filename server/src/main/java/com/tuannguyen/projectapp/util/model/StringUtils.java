package com.tuannguyen.projectapp.util.model;

public class StringUtils {
    public static String ucFirst(String str){
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
