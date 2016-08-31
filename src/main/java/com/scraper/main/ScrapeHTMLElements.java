package com.scraper.main;

import com.scraper.main.util.IntegerUtil;
import com.scraper.main.util.StringUtil;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import java.util.function.Predicate;

import static com.scraper.main.util.DateTimeUtil.transformStringDateIntoSQLFormat;
import static com.scraper.main.util.DateTimeUtil.transformStringTimeIntoSQLFormat;
import static com.scraper.main.util.StringUtil.*;

/**
 * ScrapeHTMLElements
 *
 * @author Robert Vo
 */
public class ScrapeHTMLElements {

    final static private String                 MONDAY_ABBREVIATION                 = "Mo";
    final static private String                 TUESDAY_ABBREVIATION                = "Tu";
    final static private String                 WEDNESDAY_ABBREVIATION              = "We";
    final static private String                 THURSDAY_ABBREVIATION               = "Th";
    final static private String                 FRIDAY_ABBREVIATION                 = "Fr";
    final static private String                 SATURDAY_ABBREVIATION               = "Sa";
    final static private String                 SUNDAY_ABBREVIATION                 = "Sun";
    final static private String                 EMPTY_STRING                        = "";
    final static private Predicate<Elements>    IS_NOT_EMPTY_ELEMENT                = e -> !e.isEmpty();
    final static private Predicate<Elements>    IS_ELEMENTS_SIZE_NOT_EQUAL_TO_ZERO  = e -> e.size() != 0;
    final static private Predicate<String>      IS_NOT_EMPTY                        = e -> !e.isEmpty();
    final static private Predicate<String>      IS_NOT_EMPTY_STRING                 = e -> !e.equals(EMPTY_STRING);
    final static private Predicate<String>      IS_NOT_STRING_WITH_TWO_HYPHENS      = e -> !e.equals("--");
    final static private Predicate<String>      IS_NOT_STRING_WITH_ONE_HYPHEN       = e -> !e.equals("-");
    private static Logger log = Logger.getLogger(ScrapeHTMLElements.class);

    /**
     * Retrieves the name and class number from the DOM that contains an entire class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The name and class number for a class in the format of "ABCD 1234 (99999)".
     */
    public static String getClassSubjectNumberCRN(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_NAME_AND_CRN_NUMBER);
    }

    /**
     * Retrieves the class name from the DOM that contains an entire class.
     * @param e The DOM element that contains information about an entire class.
     * @return The name for a class.
     */
    public static String getClassSubject(Element e) {
        String classSubjectNumberCRN = getClassSubjectNumberCRN(e);
        return extractTextBeforeParentheses(classSubjectNumberCRN);
    }

    /**
     * Retrieves the class course reference numbers (CRN) from the DOM that contains an entire class.
     * The CRN and class term uniquely identify a class for a given term.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The CRN for a class.
     */
    public static String getClassCRN(Element e) {
        String classNameAndCrnNumber = getClassSubjectNumberCRN(e);
        return extractTextBetweenParentheses(classNameAndCrnNumber);
    }

    /**
     * Retrieves the class title from the DOM that contains an entire class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The class title for a class.
     */
    public static String getClassTitle(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_TITLE);
    }

    /**
     * Retrieves the class status and number of seats from the DOM that contains an entire class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The class status and number of seats for a class in the format of "Open (12/35)".
     */
    public static String getClassStatusAndSeats(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_STATUS_AND_SEATS);
    }

    /**
     * Retrieves the class attributes from the DOM that contains an entire class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The class attributes for a class.
     */
    public static String getClassAttributes(Element e) {
        String attributes = getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_ATTRIBUTES);
        return attributes.isEmpty() ? "No class attributes" : attributes;
    }

    /**
     * Retrieves the class dates, including the start and end dates, from the DOM that contains an entire class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The class dates for when a class takes place, in the format of "Mon DD, YYYY – Mon DD, YYYY".
     */
    public static String getClassDates(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_DATES);
    }

    /**
     * Retrieves the start date for a class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The start date for a class, in the format of "Mon DD, YYYY".
     */
    public static String getClassStartDate(Element e) {
        Optional<String> classDatesFromHtml = Optional.ofNullable(getClassDates(e));
        return getClassDate(classDatesFromHtml, true);
    }

    /**
     * Retrieves the end date for a class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The end date for a class, in the format of "Mon DD, YYYY".
     */
    public static String getClassEndDate(Element e) {
        Optional<String> classDatesFromHtml = Optional.ofNullable(getClassDates(e));
        return getClassDate(classDatesFromHtml, false);
    }

    /**
     * Retrieves the start date, if isStart is true, or the end date, if isStart is false,
     * for a given {@param classDate} in the format of "Mon DD, YYYY - Mon DD, YYYY".
     *
     * @param classDate The class dates extracted from the DOM. Should be in the format of "Mon DD, YYYY - Mon DD, YYYY".
     * @param isStartDate Indicates whether the requested date is the start date or not.
     * @return The start date, or end date, in the format of "Mon DD, YYYY".
     */
    public static String getClassDate(Optional<String> classDate, boolean isStartDate) {
        return classDate
                    .filter(IS_NOT_EMPTY)
                    .filter(IS_NOT_EMPTY_STRING)
                    .map(e -> splitByHyphenAndExtractHalf(e, isStartDate))
                    .orElse(EMPTY_STRING);
    }

    /**
     * Retrieves the weekdays and start & end times for a class from the DOM that contains an entire class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The class days and times, in the format of "SuMoTuWeThFrSa HH:MM A.M.-HH:MM P.M.".
     */
    public static String getClassDaysAndTimes(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_DAYS_TIMES);
    }

    /**
     * Retrieves the instructor's name from the DOM that contains an entire class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The instructor's name for a class.
     */
    public static String getInstructorName(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_INSTRUCTOR);
    }

    /**
     * Retrieves the instructor's email address from the DOM that contains an entire class.
     * @param e The DOM element that contains information about an entire class.
     * @return The instructor's email for class.
     */
    public static String getInstructorEmail(Element e) {
        Elements instructorEmail = e.select(HTMLElements.CLASS_INSTRUCTOR_EMAIL.getHtml());
        return extractEmailFromHREFTag(instructorEmail);
    }

    /**
     * Retrieves the campus location where the class is being held.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The campus location where the class is being held.
     */
    public static String getCampusLocation(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_CAMPUS_LOCATION);
    }

    /**
     * Retrieves the room number where the class is being held.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The room number where the class is being held.
     */
    public static String getRoom(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_ROOM);
    }

    /**
     * Retrieves the format for a class.
     * A class format describes how the class lectures are offered.
     * Format values include: "Face To Face", "Online", "Hybrid".
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The format of a class.
     */
    public static String getFormat(Element e) {
        return getClassFromElementUsingHTMLElement(e, HTMLElements.CLASS_FORMAT);
    }

    /**
     * Retrieves the description for a class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The description for a class.
     */
    public static String getDescription(Element e) {
        return Optional.ofNullable(e.select(HTMLElements.CLASS_DESCRIPTION.getHtml()))
                    .filter(IS_NOT_EMPTY_ELEMENT)
                    .filter(IS_ELEMENTS_SIZE_NOT_EQUAL_TO_ZERO)
                    .map(ScrapeHTMLElements::getFirstChildNodeAndReturnAsString)
                    .orElse("No description available.");
    }

    /**
     * Retrieves the session which the class takes place during the semester.
     * Sessions can one of the following:
     * MIN (Mini), Regular Academic Session (session 1), 2, 3, 4, 5 (fall/spring only), 6 (fall/spring only).
     *
     * About each session -
     * Mini Sessions are typically 3 weeks preceding a full, regular, academic session.
     * Regular Academic Sessions contains the entire semester, fall/spring (15 weeks) or summer (12 weeks).
     * Session 2 - The first 4 weeks of a regular academic session.
     * Session 3 - The first half, 6 weeks, of a regular academic session.
     * Session 4 - The middle part, weeks 4 to 8, of a regular academic session.
     * Session 5 - The second half, 6 weeks, of a fall/spring regular academic session.
     * Session 6 - The last 4 weeks of a fall/spring regular academic session.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The session which the class takes place during the semester.
     */
    public static String getSession(Element e) {
        Elements classSession = e.select(HTMLElements.CLASS_SESSION.getHtml());
        return getFirstChildNodeAndReturnAsString(classSession);
    }

    /**
     * Retrieves the URL location of the syllabus, if available, for a class.
     * If the syllabus is unavailable, this method will return "Unavailable".
     *
     * @param e The DOM element that contains information about an entire class.
     * @return If available, the URL location of the syllabus for a class. Else, return "Unavailable".
     */
    public static String getSyllabus(Element e) {
        Elements classSyllabus = e.select(HTMLElements.CLASS_SYLLABUS.getHtml());
        return extractSyllabusFromElements(classSyllabus);
    }

    /**
     * Retrieves the duration of a class. The duration is typically measured in weeks.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The duration of a class, in the format of "XX Weeks".
     */
    public static String getClassDuration(Element e) {
        Elements classDuration = e.select(HTMLElements.CLASS_DURATION.getHtml());
        return getFirstChildNodeAndReturnAsString(classDuration);
    }

    /**
     * Retrieves the component of a class. The component represents the type of delivery,
     * not to be confused with format, of the class.
     *
     * Components include: LEC (Lecture), LAB (Laboratory), IND (Independent Studies).
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The component of a class.
     */
    public static String getClassComponent(Element e) {
        Elements classComponent = e.select(HTMLElements.CLASS_COMPONENT.getHtml());
        return getFirstChildNodeAndReturnAsString(classComponent);
    }

    /**
     * Retrieves the seat information for a class, in the format of (seats taken)/(total number of seats).
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The seat information of a class.
     */
    public static String getSeatInformationFrom(Element e) {
        String courseStatus = getClassStatusAndSeats(e);
        return extractTextBetweenParentheses(courseStatus);
    }

    /**
     * Retrieves the total number of seats allotted for a class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The total number of seats allotted for a class.
     */
    public static int getNumberOfTotalSeats(Element e) {
        String seatInformation = getSeatInformationFrom(e);
        String totalSeats = seatInformation.substring(seatInformation.indexOf('/') + 1);
        return IntegerUtil.parseInt(totalSeats);
    }

    /**
     * Retrieves the number of seats taken for a class.
     *
     * @param e The DOM element that contains information about an entire class.
     * @return The number of seats taken for a class.
     */
    public static int getNumberOfSeatsTaken(Element e) {
        String seatInformation = getSeatInformationFrom(e);
        String seatsTaken = seatInformation.substring(0, seatInformation.indexOf('/'));
        return IntegerUtil.parseInt(seatsTaken);
    }

    public static Class.Status getClassStatusOpenOrClosed(Element e) {
        String seatInformation = getClassStatusAndSeats(e);
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
                .filter(IS_NOT_EMPTY)
                .filter(IS_NOT_EMPTY_STRING)
                .filter(IS_NOT_STRING_WITH_TWO_HYPHENS)
                .filter(IS_NOT_STRING_WITH_ONE_HYPHEN)
                .map(e -> {
                    String wholeString = e.substring(e.indexOf(" "));
                    return splitByHyphenAndExtractHalf(wholeString, isStart);
                })
                .orElse(EMPTY_STRING);
    }

    private static String getRequestedClassTime(Element aClass, boolean half) {
        Optional<String> classTimesFromHtml = Optional.ofNullable(getClassDaysAndTimes(aClass));
        Optional<String> time = Optional.ofNullable(getClassTime(classTimesFromHtml, half));
        return time
                .filter(IS_NOT_EMPTY)
                .orElse(EMPTY_STRING);
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
        return StringUtil.splitBySpaceAndExtractHalf(courseName, false);
    }

    public static String getDepartment(String courseName) {
        return StringUtil.splitBySpaceAndExtractHalf(courseName, true);
    }

    public static int getNumberOfClasses(Document doc) {
        Elements allClasses = doc.select(HTMLElements.NUMBER_OF_CLASSES.getHtml());
        return IntegerUtil.parseInt(extractTextBetweenParentheses(allClasses));
    }

    public static Class convertElementToAClass(Element aClass) {
        Term            termID                  = Term.returnTermFromString(String.valueOf(URLBuilder.extractTermParameter(aClass.baseUri())));
        String          classTitle              = getClassTitle(aClass);
        String          className               = getClassSubject(aClass);
        String          departmentName          = splitBySpaceAndExtractHalf(className, true);
        String          departmentCourseNumber  = splitBySpaceAndExtractHalf(className, false);
        Class.Status    classStatus             = getClassStatusOpenOrClosed(aClass);
        String          courseNumber            = getClassCRN(aClass);
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
        String          location                = getCampusLocation(aClass);
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
