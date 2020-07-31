package com.mirocidij.simpleconsoleapplication.utils;

public class ParseUtils {
    public static Integer TryParseInt(String num) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
