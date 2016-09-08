package com.scraper.main.util;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * DataTimeUtil contains methods that will assist with transforming
 * a String representation of date/time to a valid java.sql.Date/java.sql.Time object.
 *
 * @author Robert Vo
 */
public class DateTimeUtil {
    final static String MONTH_DAY_YEAR_FORMAT = "MMMM d, yyyy";
    final static String TIME_FORMAT_12_HOUR   = "hh:mm a";
    private static Logger log = Logger.getLogger(DateTimeUtil.class);

    /**
     * Transforms a String representation of a date to a valid java.sql.Date object.
     *
     * @param stringDate A String representation of a date.
     * @return A java.sql.Date object that represents the String in the proper format.
     */
    public static Date transformStringDateIntoSQLFormat(String stringDate) {
        final SimpleDateFormat format = new SimpleDateFormat(MONTH_DAY_YEAR_FORMAT, Locale.ENGLISH);
        try {
            final java.util.Date date = format.parse(stringDate);
            return new java.sql.Date(date.getTime());
        }
        catch (ParseException | NullPointerException e) {
            if(stringDate == null) {
                return null;
            }
            if(!stringDate.equals("") && !stringDate.equals("A.M.") && !stringDate.equals("P.M.")) {
                log.warn("Unable to convert date. Given an error of: " + e);
            }
            return null;
        }
    }

    /**
     * Transforms a String representation of a time to a valid java.sql.Time object.
     *
     * @param stringTime A String representation of a time.
     * @return A java.sql.Time object that represents the String in the proper format.
     */
    public static Time transformStringTimeIntoSQLFormat(String stringTime) {
        final SimpleDateFormat date12Format = new SimpleDateFormat(TIME_FORMAT_12_HOUR, Locale.ENGLISH);
        try {
            final String stringTimeToConvert = stringTime.replace(".", "");
            java.util.Date time = date12Format.parse(stringTimeToConvert);
            return new java.sql.Time(time.getTime());
        }
        catch (ParseException | NullPointerException e) {
            if(stringTime == null) {
                return null;
            }
            else if(!stringTime.equals("") && !stringTime.equals("A.M.") && !stringTime.equals("P.M.")) {
                log.warn("Unable to convert time. Given an error of: " + e);
            }
            return null;
        }
    }
}
