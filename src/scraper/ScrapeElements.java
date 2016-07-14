package scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ScrapeElements {

    public static String getNameAndCourseNumber(Element e) {
        return e.select(HTMLElements.CLASS_NAME_AND_CRN_NUMBER.getHtml()).text();
    }

    public static String getCourseTitle(Element e) {
        return e.select(HTMLElements.COURSE_TITLE.getHtml()).text();
    }

    public static String getCourseStatus(Element e) {
        return e.select(HTMLElements.CLASS_STATUS.getHtml()).text();
    }

    public static String getClassAttributes(Element e) {
        String attributes = e.select(HTMLElements.CLASS_ATTRIBUTES.getHtml()).text();
        return attributes.isEmpty() ? "No class attributes" : attributes;
    }

    public static String getClassDates(Element e) {
        return e.select(HTMLElements.CLASS_DATES.getHtml()).get(0).text();
    }

    public static String getClassDaysAndTimes(Element e) {
        return e.select(HTMLElements.CLASS_DAYS_TIMES.getHtml()).get(0).text();
    }

    public static String getInstructorName(Element e) {
        return e.select(HTMLElements.CLASS_INSTRUCTOR.getHtml()).text();
    }

    public static String getInstructorEmail(Element e) {
        return e.select(HTMLElements.CLASS_INSTRUCTOR_EMAIL.getHtml())
                .attr("href")
                .replaceAll("[\\t\\n]", "")
                .substring(7);
    }

    public static String getLocation(Element e) {
        return e.select(HTMLElements.CLASS_LOCATION.getHtml()).get(0).text();
    }

    public static String getRoom(Element e) {
        return e.select(HTMLElements.CLASS_LOCATION.getHtml()).get(1).text();
    }

    public static String getFormat(Element e) {
        return e.select(HTMLElements.CLASS_FORMAT.getHtml()).text();
    }

    public static String getDescription(Element e) {
        return e.select(HTMLElements.CLASS_DESCRIPTION.getHtml()).text();
    }

    public static String getCourseNumber(Element e) {
        try {
            return e.select(HTMLElements.CLASS_NAME_AND_CRN_NUMBER
                    .getHtml())
                    .first()
                    .text()
                    .split(Scraper.REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES)[1];
        }
        catch (Exception ex) {
            return "0";
        }
    }

    public static String getSession(Element e) {
        return e.select(HTMLElements.CLASS_SESSION.getHtml()).text();
    }

    public static String getSyllabus(Element e) {
        return e.select(HTMLElements.CLASS_SYLLABUS.getHtml()).text();
    }

    public static String getLastDayToAdd(Element e) {
        return e.select(HTMLElements.CLASS_DATES.getHtml()).text();
    }

    public static String getClassDuration(Element e) {
        return e.select(HTMLElements.CLASS_DURATION.getHtml())
                .first()
                .childNode(1)
                .toString()
                .trim();
    }

    public static String getClassComponent(Element e) {
        return e.select(HTMLElements.CLASS_COMPONENT.getHtml())
                .first()
                .childNode(1)
                .toString()
                .trim();
    }

    public static String getLastDateToDrop(Element e) {
        return e.select(HTMLElements.CLASS_DATES.getHtml()).text();
    }


}
