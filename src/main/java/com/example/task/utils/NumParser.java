package com.example.task.utils;

public class NumParser {
    public static Long parseStringAsLong (String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
