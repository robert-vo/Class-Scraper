package com.scraper.main.util;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateTimeUtil {
    static final String MONTH_DAY_YEAR_FORMAT = "MMMM d, yyyy";
    static final String TIME_FORMAT_12_HOUR   = "hh:mm a";

    public static Date transformStringDateIntoSQLFormat(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat(MONTH_DAY_YEAR_FORMAT, Locale.ENGLISH);
        try {
            java.util.Date date = format.parse(stringDate);
            return new java.sql.Date(date.getTime());
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Time transformStringTimeIntoSQLFormat(String stringTime) {
        SimpleDateFormat date12Format = new SimpleDateFormat(TIME_FORMAT_12_HOUR, Locale.ENGLISH);
        try {
            String stringTimeToConvert = stringTime.replace(".", "");
            java.util.Date time = date12Format.parse(stringTimeToConvert);
            return new java.sql.Time(time.getTime());
        }
        catch (Exception e) {
            return null;
        }
    }
}
