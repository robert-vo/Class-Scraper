package scraper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

public class ScrapeElements implements Scraper {

    final static private String MONDAY_ABBREVIATION    = "Mo";
    final static private String TUESDAY_ABBREVIATION   = "Tu";
    final static private String WEDNESDAY_ABBREVIATION = "We";
    final static private String THURSDAY_ABBREVIATION  = "Th";
    final static private String FRIDAY_ABBREVIATION    = "Fr";
    final static private String SATURDAY_ABBREVIATION  = "Sa";
    final static private String SUNDAY_ABBREVIATION    = "Sun";

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

    public static String getClassStartDate(Element e) {
        Optional<String> classDatesFromHtml = Optional.ofNullable(getClassDates(e));
        return getClassDate(classDatesFromHtml, true);
    }

    public static String getClassEndDate(Element e) {
        Optional<String> classDatesFromHtml = Optional.ofNullable(getClassDates(e));
        return getClassDate(classDatesFromHtml, false);
    }

    public static String getClassDate(Optional<String> classDate, boolean isStart) {
        if(classDate.isPresent() && !classDate.get().equals("")) {
            String bothClassTimes = classDate.get();
            return Scraper.splitByHyphenAndExtractHalf(bothClassTimes, isStart);
        }
        else {
            return "";
        }
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
        Optional<Elements> classDescription = Optional.ofNullable(e.select(HTMLElements.CLASS_DESCRIPTION.getHtml()));

        if(classDescription.isPresent()) {
            return getFirstChildNodeAndReturnAsString(classDescription.get());
        }
        else {
            return "No description available.";
        }
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
        return extractSyllabusFromElements(classSyllabus);
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

    public static int getNumberOfTotalSeats(Element e) {
        String seatInformation = getSeatInformationFrom(e);
        String totalSeats = seatInformation.substring(seatInformation.indexOf('/') + 1);
        return Integer.parseInt(totalSeats);
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
        Optional<Elements> scrapedElement = Optional.ofNullable(e.select(htmlElement.getHtml()));

        if(scrapedElement.isPresent()) {
            return scrapedElement.get().text();
        }
        else {
            return "";
        }
    }

    public static String extractSyllabusFromElements(Elements e) {
        return e.size() == 0 ? "Unavailable" : e.attr("href");
    }

    private static String getClassTime(Optional<String> classTime, boolean isStart) {
        if(classTime.isPresent() && !classTime.get().equals("") && !classTime.get().equals("--") && !classTime.get().equals("-")) {
            String bothClassTimes = classTime.get();
            String wholeString = bothClassTimes.substring(bothClassTimes.indexOf(" "));
            return Scraper.splitByHyphenAndExtractHalf(wholeString, isStart);
        }
        else {
            return "";
        }
    }

    public static String getClassEndTime(Element aClass) {
        Optional<String> classTimesFromHtml = Optional.ofNullable(getClassDaysAndTimes(aClass));
        return getClassTime(classTimesFromHtml, false);
    }

    public static String getClassStartTime(Element aClass) {
        Optional<String> classTimesFromHtml = Optional.ofNullable(getClassDaysAndTimes(aClass));
        return getClassTime(classTimesFromHtml, true);
    }

    private static boolean isClassOnCertainDay(Element aClass, String classDay) {
        String classDays = getClassDaysAndTimes(aClass);
        return classDays.contains(classDay);
    }

    public static boolean isMondayClass(Element aClass) {
        return isClassOnCertainDay(aClass, MONDAY_ABBREVIATION);
    }

    public static boolean isTuesdayClass(Element aClass) {
        return isClassOnCertainDay(aClass, TUESDAY_ABBREVIATION);
    }

    public static boolean isWednesdayClass(Element aClass) {
        return isClassOnCertainDay(aClass, WEDNESDAY_ABBREVIATION);
    }

    public static boolean isThursdayClass(Element aClass) {
        return isClassOnCertainDay(aClass, THURSDAY_ABBREVIATION);
    }

    public static boolean isFridayClass(Element aClass) {
        return isClassOnCertainDay(aClass, FRIDAY_ABBREVIATION);
    }

    public static boolean isSaturdayClass(Element aClass) {
        return isClassOnCertainDay(aClass, SATURDAY_ABBREVIATION);
    }

    public static boolean isSundayClass(Element aClass) {
        return isClassOnCertainDay(aClass, SUNDAY_ABBREVIATION);
    }

}
