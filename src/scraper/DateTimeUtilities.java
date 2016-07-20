package scraper;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateTimeUtilities {
    static final String MONTH_DAY_YEAR_FORMAT = "MMMM d, yyyy";
    static final String TIME_FORMAT_12_HOUR   = "HH:mm a";
    static final String TIME_FORMAT_24_HOUR   = "HH:mm";

    static Date transformStringDateIntoSQLFormat(String stringDate) {
        DateFormat format = new SimpleDateFormat(MONTH_DAY_YEAR_FORMAT, Locale.ENGLISH);
        java.sql.Date dateToReturn = null;
        try {
            java.util.Date date = format.parse(stringDate);
            dateToReturn = new java.sql.Date(date.getTime());
            return dateToReturn;
        }
        catch (Exception e) {
            return null;
        }
    }

    static Time transformStringtimeIntoSQLFormat(String stringTime) {
        return null;
    }

    /*

            // TODO - Refactor this mess. Include the logic in Class?
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm a", Locale.ENGLISH); // 12 hour format
            java.sql.Time ppstime1 = null;
            java.sql.Time ppstime2 = null;

            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

            try {
                ppstime1 = new java.sql.Time(date12Format.parse(c.getClassStartTime().replace(".", "")).getTime());
                ppstime2 = new java.sql.Time(date12Format.parse(c.getClassEndTime().replace(".", "")).getTime());
            }
            catch (Exception e) {
                ScraperGUI.appendToLoggerTextArea("Invalid date");
            }

            preparedStatement.setTime (10, ppstime1);
            preparedStatement.setTime (11, ppstime2);

     */
}
