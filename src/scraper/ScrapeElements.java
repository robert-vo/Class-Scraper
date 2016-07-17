package scraper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapeElements implements Scraper {

    public static String getNameAndCourseNumber(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_NAME_AND_CRN_NUMBER);
    }

    public static String getCourseTitle(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.COURSE_TITLE);
    }

    public static String getCourseStatusAndSeats(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_STATUS_AND_SEATS);
    }

    public static String getClassAttributes(Element e) {
        String attributes = getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_ATTRIBUTES);
        return attributes.isEmpty() ? "No class attributes" : attributes;
    }

    public static String getClassDates(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_DATES);
    }

    public static String getClassDaysAndTimes(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_DAYS_TIMES);
    }

    public static String getInstructorName(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_INSTRUCTOR);
    }

    public static String getInstructorEmail(Element e) {
        Elements instructorEmail = e.select(HTMLElements.CLASS_INSTRUCTOR_EMAIL.getHtml());
        return Scraper.extractEmailFromHREFTag(instructorEmail);
    }

    public static String getLocation(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_LOCATION);
    }

    public static String getRoom(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_ROOM);
    }

    public static String getFormat(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_FORMAT);
    }

    public static String getDescription(Element e) {
        Elements classDescription = e.select(HTMLElements.CLASS_DESCRIPTION.getHtml());
        return getFirstChildNodeAndReturnAsString(classDescription);
    }

    public static String getCourseName(Element e) {
        String classNameAndCrnNumber = getNameAndCourseNumber(e);
        return Scraper.extractTextBeforeParentheses(classNameAndCrnNumber);
    }

    public static String getCourseNumber(Element e) {
        String classNameAndCrnNumber = getNameAndCourseNumber(e);
        return Scraper.extractTextBetweenParentheses(classNameAndCrnNumber);
    }

    public static String getSession(Element e) {
        Elements classSession = e.select(HTMLElements.CLASS_SESSION.getHtml());
        return getFirstChildNodeAndReturnAsString(classSession);
    }

    public static String getSyllabus(Element e) {
        Elements classSyllabus = e.select(HTMLElements.CLASS_SYLLABUS.getHtml());
        return getFirstChildNodeAndReturnAsString(classSyllabus);
    }

    public static String getClassDuration(Element e) {
        Elements classDuration = e.select(HTMLElements.CLASS_DURATION.getHtml());
        return getFirstChildNodeAndReturnAsString(classDuration);
    }

    public static String getClassComponent(Element e) {
        Elements classComponent = e.select(HTMLElements.CLASS_COMPONENT.getHtml());
        return getFirstChildNodeAndReturnAsString(classComponent);
    }

    public static String getSeatInformationFrom(Element e) {
        String courseStatus = getCourseStatusAndSeats(e);
        return Scraper.extractTextBetweenParentheses(courseStatus);
    }

    public static int getNumberOfAvailableSeats(Element e) {
        String seatInformation = getSeatInformationFrom(e);
        String availableSeats = seatInformation.substring(seatInformation.indexOf('/') + 1);
        return Integer.parseInt(availableSeats);
    }

    public static int getNumberOfSeatsTaken(Element e) {
        String seatInformation = getSeatInformationFrom(e);
        String seatsTaken = seatInformation.substring(0, seatInformation.indexOf('/'));
        return Integer.parseInt(seatsTaken);
    }

    public static Class.Status getCourseStatusOpenOrClosed(Element e) {
        String seatInformation = getCourseStatusAndSeats(e);
        return Class.Status.valueOf(seatInformation.substring(0, seatInformation.indexOf('(') - 1).trim());
    }

    private static String getFirstChildNodeAndReturnAsString(Elements e) {
        return e.first()
                .childNode(1)
                .toString()
                .trim();
    }

    public static String getClassFromElementUsingHTMLElement(Element e, HTMLElements htmlElement) {
        return e.select(htmlElement.getHtml())
                .text();
    }

}
