package com.mirocidij.simpleconsoleapplication.utils;

public class ParseUtils {
    public static Integer TryParseInt(String num) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long tryParseLong(String num) {
        try {
            return Long.parseLong(num);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
