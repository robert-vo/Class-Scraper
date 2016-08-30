package com.scraper.main.util;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateTimeUtil {
    final static String MONTH_DAY_YEAR_FORMAT = "MMMM d, yyyy";
    final static String TIME_FORMAT_12_HOUR   = "hh:mm a";

    public static Date transformStringDateIntoSQLFormat(String stringDate) {
        final SimpleDateFormat format = new SimpleDateFormat(MONTH_DAY_YEAR_FORMAT, Locale.ENGLISH);
        try {
            final java.util.Date date = format.parse(stringDate);
            return new java.sql.Date(date.getTime());
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Time transformStringTimeIntoSQLFormat(String stringTime) {
        final SimpleDateFormat date12Format = new SimpleDateFormat(TIME_FORMAT_12_HOUR, Locale.ENGLISH);
        try {
            final String stringTimeToConvert = stringTime.replace(".", "");
            java.util.Date time = date12Format.parse(stringTimeToConvert);
            return new java.sql.Time(time.getTime());
        }
        catch (Exception e) {
            return null;
        }
    }
}
