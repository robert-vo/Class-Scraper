package scraper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapeElements implements Scraper {

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
        return e.select(HTMLElements.CLASS_DATES.getHtml()).text();
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
        return e.select(HTMLElements.CLASS_LOCATION.getHtml()).text();
    }

    public static String getRoom(Element e) {
        return e.select(HTMLElements.CLASS_ROOM.getHtml()).text();
    }

    public static String getFormat(Element e) {
        return e.select(HTMLElements.CLASS_FORMAT.getHtml()).text();
    }

    public static String getDescription(Element e) {
        Elements classDescription = e.select(HTMLElements.CLASS_DESCRIPTION.getHtml());
        return getFirstChildNodeAndReturnAsString(classDescription);
    }

    public static String getCourseNumber(Element e) {
        try {
            Elements classNameAndCrnNumber = e.select(HTMLElements.CLASS_NAME_AND_CRN_NUMBER.getHtml());
            return Scraper.extractTextBetweenParentheses(classNameAndCrnNumber);
        }
        catch (Exception ex) {
            return "0";
        }
    }

    public static String getSession(Element e) {
        Elements classSession = e.select(HTMLElements.CLASS_SESSION.getHtml());
        return getFirstChildNodeAndReturnAsString(classSession);
    }

    public static String getSyllabus(Element e) {
        Elements classSyllabus = e.select(HTMLElements.CLASS_SYLLABUS.getHtml());
        return getFirstChildNodeAndReturnAsString(classSyllabus);
    }

    public static String getLastDayToAdd(Element e) {
        return e.select(HTMLElements.CLASS_DATES.getHtml()).text();
    }

    public static String getClassDuration(Element e) {
        Elements classDuration = e.select(HTMLElements.CLASS_DURATION.getHtml());
        return getFirstChildNodeAndReturnAsString(classDuration);
    }

    public static String getClassComponent(Element e) {
        Elements classComponent = e.select(HTMLElements.CLASS_COMPONENT.getHtml());
        return getFirstChildNodeAndReturnAsString(classComponent);
    }

    public static String getLastDateToDrop(Element e) {
        return e.select(HTMLElements.CLASS_DATES.getHtml()).text();
    }


    private static String getFirstChildNodeAndReturnAsString(Elements e) {
        return e.first()
                .childNode(1)
                .toString()
                .trim();
    }
}
