package com.scraper.main.util;


import org.apache.log4j.Logger;

/**
 * IntegerUtil contains methods that will assist with transforming
 * a String representation of an integer to an int.
 *
 * @author Robert Vo
 */
public class IntegerUtil {

    private static Logger log = Logger.getLogger(IntegerUtil.class);

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
            log.warn("NumberFormatException thrown while trying to parse " + str + " as an integer. Returning 0.");
            return 0;
        }
    }

}
