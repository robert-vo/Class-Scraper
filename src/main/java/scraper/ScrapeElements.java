package main.java.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import java.util.function.Predicate;

import static main.java.scraper.DateTimeUtilities.transformStringDateIntoSQLFormat;
import static main.java.scraper.DateTimeUtilities.transformStringTimeIntoSQLFormat;
import static main.java.scraper.StringUtilities.*;

public class ScrapeElements {

    final static private String MONDAY_ABBREVIATION    = "Mo";
    final static private String TUESDAY_ABBREVIATION   = "Tu";
    final static private String WEDNESDAY_ABBREVIATION = "We";
    final static private String THURSDAY_ABBREVIATION  = "Th";
    final static private String FRIDAY_ABBREVIATION    = "Fr";
    final static private String SATURDAY_ABBREVIATION  = "Sa";
    final static private String SUNDAY_ABBREVIATION    = "Sun";
    final static private Predicate<String> isNotEmpty = e -> !e.isEmpty();
    final static private Predicate<Elements> isNotEmptyElement = e -> !e.isEmpty();

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
        return classDate
                    .filter(isNotEmpty)
                    .filter(e -> !e.equals(""))
                    .map(e -> splitByHyphenAndExtractHalf(e, isStart))
                    .orElse("");
    }

    public static String getClassDaysAndTimes(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_DAYS_TIMES);
    }

    public static String getInstructorName(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_INSTRUCTOR);
    }

    public static String getInstructorEmail(Element e) {
        Elements instructorEmail = e.select(HTMLElements.CLASS_INSTRUCTOR_EMAIL.getHtml());
        return extractEmailFromHREFTag(instructorEmail);
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
        return Optional.ofNullable(e.select(HTMLElements.CLASS_DESCRIPTION.getHtml()))
                .filter(isNotEmptyElement)
                .filter(e1 -> e1.size() != 0)
                .map(ScrapeElements::getFirstChildNodeAndReturnAsString)
                .orElse("No description available.");
    }

    public static String getCourseName(Element e) {
        String classNameAndCrnNumber = getNameAndCourseNumber(e);
        return extractTextBeforeParentheses(classNameAndCrnNumber);
    }

    public static String getCourseNumber(Element e) {
        String classNameAndCrnNumber = getNameAndCourseNumber(e);
        return extractTextBetweenParentheses(classNameAndCrnNumber);
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
        return extractTextBetweenParentheses(courseStatus);
    }

    public static int getNumberOfTotalSeats(Element e) {
        String seatInformation = getSeatInformationFrom(e);
        String totalSeats = seatInformation.substring(seatInformation.indexOf('/') + 1);
        return parseInt(totalSeats);
    }

    public static int getNumberOfSeatsTaken(Element e) {
        String seatInformation = getSeatInformationFrom(e);
        String seatsTaken = seatInformation.substring(0, seatInformation.indexOf('/'));
        return parseInt(seatsTaken);
    }

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public static Class.Status getCourseStatusOpenOrClosed(Element e) {
        String seatInformation = getCourseStatusAndSeats(e);
        return Class.Status.valueOf(seatInformation.substring(0, seatInformation.indexOf('(') - 1).trim());
    }

    private static String getFirstChildNodeAndReturnAsString(Elements e) {
        return e.stream()
                .findFirst()
                .map(e1 -> e1.childNode(1))
                .map(Node::toString)
                .map(String::trim)
                .get();
    }

    public static String getClassFromElementUsingHTMLElement(Element e, HTMLElements htmlElement) {
        return Optional
                .ofNullable(e.select(htmlElement.getHtml()))
                .orElse(new Elements(1))
                .text();
    }

    public static String extractSyllabusFromElements(Elements e) {
        return e.size() == 0 ? "Unavailable" : e.attr("href");
    }

    private static String getClassTime(Optional<String> classTime, boolean isStart) {
        return classTime
                .filter(isNotEmpty)
                .filter(e -> !e.equals(""))
                .filter(e -> !e.equals("--"))
                .filter(e -> !e.equals("-"))
                .map(e -> {
                    String wholeString = e.substring(e.indexOf(" "));
                    return splitByHyphenAndExtractHalf(wholeString, isStart);
                })
                .orElse("");
    }

    private static String getRequestedClassTime(Element aClass, boolean half) {
        Optional<String> classTimesFromHtml = Optional.ofNullable(getClassDaysAndTimes(aClass));
        Optional<String> time = Optional.ofNullable(getClassTime(classTimesFromHtml, half));
        return time
                .filter(isNotEmpty)
                .orElse("");
    }

    public static String getClassEndTime(Element aClass) {
        return getRequestedClassTime(aClass, false);
    }

    public static String getClassStartTime(Element aClass) {
        return getRequestedClassTime(aClass, true);
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

    public static String getDepartmentCourseNumber(String courseName) {
        return StringUtilities.splitBySpaceAndExtractHalf(courseName, false);
    }

    public static String getDepartment(String courseName) {
        return StringUtilities.splitBySpaceAndExtractHalf(courseName, true);
    }

    public static int getNumberOfClasses(Document doc) {
        Elements allClasses = doc.select(HTMLElements.NUMBER_OF_CLASSES.getHtml());
        return parseInt(extractTextBetweenParentheses(allClasses));
    }

    public static Class convertElementToAClass(Element aClass) {
        Term            termID                  = Term.returnTermFromString(String.valueOf(URLBuilder.extractTermParameter(aClass.baseUri())));
        String          classTitle              = getCourseTitle(aClass);
        String          className               = getCourseName(aClass);
        String          departmentName          = splitBySpaceAndExtractHalf(className, true);
        String          departmentCourseNumber  = splitBySpaceAndExtractHalf(className, false);
        Class.Status    classStatus             = getCourseStatusOpenOrClosed(aClass);
        String          courseNumber            = getCourseNumber(aClass);
        int             seatsTaken              = getNumberOfSeatsTaken(aClass);
        int             seatsTotal              = getNumberOfTotalSeats(aClass);
        int             seatsAvailable          = seatsTotal - seatsTaken;
        Date            classStartDate          = transformStringDateIntoSQLFormat(getClassStartDate(aClass));
        Date            classEndDate            = transformStringDateIntoSQLFormat(getClassEndDate(aClass));
        String          attributes              = getClassAttributes(aClass);
        Time            classStartTime          = transformStringTimeIntoSQLFormat(getClassStartTime(aClass));
        Time            classEndTime            = transformStringTimeIntoSQLFormat(getClassEndTime(aClass));
        boolean         isMondayClass           = isMondayClass(aClass);
        boolean         isTuesdayClass          = isTuesdayClass(aClass);
        boolean         isWednesdayClass        = isWednesdayClass(aClass);
        boolean         isThursdayClass         = isThursdayClass(aClass);
        boolean         isFridayClass           = isFridayClass(aClass);
        boolean         isSaturdayClass         = isSaturdayClass(aClass);
        boolean         isSundayClass           = isSundayClass(aClass);
        String          instructorName          = getInstructorName(aClass);
        String          instructorEmail         = getInstructorEmail(aClass);
        String          location                = getLocation(aClass);
        String          room                    = getRoom(aClass);
        String          buildingAbbreviation    = splitBySpaceAndExtractHalf(room, true);
        String          buildingRoomNumber      = splitBySpaceAndExtractHalf(room, false);
        String          format                  = getFormat(aClass);
        String          description             = getDescription(aClass);
        String          duration                = getClassDuration(aClass);
        String          session                 = getSession(aClass);
        String          component               = getClassComponent(aClass);
        String          syllabus                = getSyllabus(aClass);
        return new Class(termID, classTitle, departmentName, departmentCourseNumber, classStatus, courseNumber, seatsTaken, seatsAvailable,
                seatsTotal, classStartDate, classEndDate, attributes, classStartTime, classEndTime,
                isMondayClass, isTuesdayClass, isWednesdayClass, isThursdayClass, isFridayClass, isSaturdayClass,
                isSundayClass, instructorName, instructorEmail, location, buildingAbbreviation, buildingRoomNumber, format,
                description, duration, session, component, syllabus);
    }
}
