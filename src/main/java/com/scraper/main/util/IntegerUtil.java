package com.scraper.main.util;


/**
 * IntegerUtil contains methods that will assist with transforming
 * a String representation of an integer to an int.
 *
 * @author Robert Vo
 */
public class IntegerUtil {

    /**
     * Transforms a String representation of a number into an int.
     * If the transformation fails due to an invalid String, the method will return 0.
     *
     * @param str The String representation of a number.
     * @return An integer transformed from the String.
     */
    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException nfe) {
            return 0;
        }
    }

}
