package com.scraper.main.util;


public class IntegerUtil {

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException nfe) {
            return 0;
        }
    }

}
