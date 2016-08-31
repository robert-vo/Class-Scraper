package com.scraper.main;

import com.scraper.main.util.IntegerUtil;
import com.scraper.main.util.StringUtil;
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
 * ScrapeHTMLElements contains methods that scrape the DOM for pertinent information about a class and
 * constructs a Class object from the scraped information.
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

    /**
     * Retrieves the name and class number from the DOM that contains an entire class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The name and class number for a class in the format of "ABCD 1234 (99999)".
     */
    public static String getClassSubjectNumberCRN(Element aClass) {
        return getClassFromElementUsingHTMLElement(aClass, HTMLElements.CLASS_NAME_AND_CRN_NUMBER);
    }

    /**
     * Retrieves the class name from the DOM that contains an entire class.
     * @param aClass The DOM element that contains information about an entire class.
     * @return The name for a class.
     */
    public static String getClassSubject(Element aClass) {
        String classSubjectNumberCRN = getClassSubjectNumberCRN(aClass);
        return extractTextBeforeParentheses(classSubjectNumberCRN);
    }

    /**
     * Retrieves the class course reference numbers (CRN) from the DOM that contains an entire class.
     * The CRN and class term uniquely identify a class for a given term.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The CRN for a class.
     */
    public static String getClassCRN(Element aClass) {
        String classNameAndCrnNumber = getClassSubjectNumberCRN(aClass);
        return extractTextBetweenParentheses(classNameAndCrnNumber);
    }

    /**
     * Retrieves the class title from the DOM that contains an entire class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The class title for a class.
     */
    public static String getClassTitle(Element aClass) {
        return getClassFromElementUsingHTMLElement(aClass, HTMLElements.CLASS_TITLE);
    }

    /**
     * Retrieves the class status and number of seats from the DOM that contains an entire class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The class status and number of seats for a class in the format of "Open (12/35)".
     */
    public static String getClassStatusAndSeats(Element aClass) {
        return getClassFromElementUsingHTMLElement(aClass, HTMLElements.CLASS_STATUS_AND_SEATS);
    }

    /**
     * Retrieves the class attributes from the DOM that contains an entire class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The class attributes for a class.
     */
    public static String getClassAttributes(Element aClass) {
        String attributes = getClassFromElementUsingHTMLElement(aClass, HTMLElements.CLASS_ATTRIBUTES);
        return attributes.isEmpty() ? "No class attributes" : attributes;
    }

    /**
     * Retrieves the class dates, including the start and end dates, from the DOM that contains an entire class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The class dates for when a class takes place, in the format of "Mon DD, YYYY – Mon DD, YYYY".
     */
    public static String getClassDates(Element aClass) {
        return getClassFromElementUsingHTMLElement(aClass, HTMLElements.CLASS_DATES);
    }

    /**
     * Retrieves the start date for a class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The start date for a class, in the format of "Mon DD, YYYY".
     */
    public static String getClassStartDate(Element aClass) {
        Optional<String> classDatesFromHtml = Optional.ofNullable(getClassDates(aClass));
        return getClassDate(classDatesFromHtml, true);
    }

    /**
     * Retrieves the end date for a class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The end date for a class, in the format of "Mon DD, YYYY".
     */
    public static String getClassEndDate(Element aClass) {
        Optional<String> classDatesFromHtml = Optional.ofNullable(getClassDates(aClass));
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
     * @param aClass The DOM element that contains information about an entire class.
     * @return The class days and times, in the format of "SunMoTuWeThFrSa HH:MM A.M.-HH:MM P.M.".
     */
    public static String getClassDaysAndTimes(Element aClass) {
        return getClassFromElementUsingHTMLElement(aClass, HTMLElements.CLASS_DAYS_TIMES);
    }

    /**
     * Retrieves the instructor's name from the DOM that contains an entire class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The instructor's name for a class.
     */
    public static String getInstructorName(Element aClass) {
        return getClassFromElementUsingHTMLElement(aClass, HTMLElements.CLASS_INSTRUCTOR);
    }

    /**
     * Retrieves the instructor's email address from the DOM that contains an entire class.
     * @param aClass The DOM element that contains information about an entire class.
     * @return The instructor's email for class.
     */
    public static String getInstructorEmail(Element aClass) {
        Elements instructorEmail = aClass.select(HTMLElements.CLASS_INSTRUCTOR_EMAIL.getHtml());
        return extractEmailFromHREFTag(instructorEmail);
    }

    /**
     * Retrieves the campus location where the class is being held.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The campus location where the class is being held.
     */
    public static String getCampusLocation(Element aClass) {
        return getClassFromElementUsingHTMLElement(aClass, HTMLElements.CLASS_CAMPUS_LOCATION);
    }

    /**
     * Retrieves the room number where the class is being held.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The room number where the class is being held.
     */
    public static String getRoom(Element aClass) {
        return getClassFromElementUsingHTMLElement(aClass, HTMLElements.CLASS_ROOM);
    }

    /**
     * Retrieves the format for a class.
     * A class format describes how the class lectures are offered.
     * Format values include: "Face To Face", "Online", "Hybrid".
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The format of a class.
     */
    public static String getFormat(Element aClass) {
        return getClassFromElementUsingHTMLElement(aClass, HTMLElements.CLASS_FORMAT);
    }

    /**
     * Retrieves the description for a class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The description for a class.
     */
    public static String getDescription(Element aClass) {
        return Optional.ofNullable(aClass.select(HTMLElements.CLASS_DESCRIPTION.getHtml()))
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
     * @param aClass The DOM element that contains information about an entire class.
     * @return The session which the class takes place during the semester.
     */
    public static String getSession(Element aClass) {
        Elements classSession = aClass.select(HTMLElements.CLASS_SESSION.getHtml());
        return getFirstChildNodeAndReturnAsString(classSession);
    }

    /**
     * Retrieves the syllabus from the class information, if one exists.
     *
     * @param aClass The DOM element that contains information about an entire class.
     *               In this instance, it is represented as Elements to see if the anchor tag contains
     *               a syllabus or not.
     * @return The DOM that contains the syllabus, as a String,
     * or "Unavailable" if the syllabus does not exist.
     */
    public static String extractSyllabusFromElements(Elements aClass) {
        return aClass.size() == 0 ? "Unavailable" : aClass.attr("href");
    }

    /**
     * Retrieves the URL location of the syllabus, if available, for a class.
     * If the syllabus is unavailable, this method will return "Unavailable".
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return If available, the URL location of the syllabus for a class. Else, return "Unavailable".
     */
    public static String getSyllabus(Element aClass) {
        Elements classSyllabus = aClass.select(HTMLElements.CLASS_SYLLABUS.getHtml());
        return extractSyllabusFromElements(classSyllabus);
    }

    /**
     * Retrieves the duration of a class. The duration is typically measured in weeks.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The duration of a class, in the format of "XX Weeks".
     */
    public static String getClassDuration(Element aClass) {
        Elements classDuration = aClass.select(HTMLElements.CLASS_DURATION.getHtml());
        return getFirstChildNodeAndReturnAsString(classDuration);
    }

    /**
     * Retrieves the component of a class. The component represents the type of delivery,
     * not to be confused with format, of the class.
     *
     * Components include: LEC (Lecture), LAB (Laboratory), IND (Independent Studies).
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The component of a class.
     */
    public static String getClassComponent(Element aClass) {
        Elements classComponent = aClass.select(HTMLElements.CLASS_COMPONENT.getHtml());
        return getFirstChildNodeAndReturnAsString(classComponent);
    }

    /**
     * Retrieves the seat information for a class, in the format of (seats taken)/(total number of seats).
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The seat information of a class.
     */
    public static String getSeatInformationFrom(Element aClass) {
        String courseStatus = getClassStatusAndSeats(aClass);
        return extractTextBetweenParentheses(courseStatus);
    }

    /**
     * Retrieves the total number of seats allotted for a class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The total number of seats allotted for a class.
     */
    public static int getNumberOfTotalSeats(Element aClass) {
        String seatInformation = getSeatInformationFrom(aClass);
        String totalSeats = seatInformation.substring(seatInformation.indexOf('/') + 1);
        return IntegerUtil.parseInt(totalSeats);
    }

    /**
     * Retrieves the number of seats taken for a class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The number of seats taken for a class.
     */
    public static int getNumberOfSeatsTaken(Element aClass) {
        String seatInformation = getSeatInformationFrom(aClass);
        String seatsTaken = seatInformation.substring(0, seatInformation.indexOf('/'));
        return IntegerUtil.parseInt(seatsTaken);
    }

    /**
     * Retrieves the status, which can be either Open or Closed, for a class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The status of a class.
     */
    public static Class.Status getClassStatusOpenOrClosed(Element aClass) {
        String seatInformation = getClassStatusAndSeats(aClass);
        return Class.Status.valueOf(seatInformation.substring(0, seatInformation.indexOf('(') - 1).trim());
    }

    /**
     * Retrieves the first child node, transform the node into a String, and trims the String, and returns the value.
     * Example:
     *      e.toString() = <li><strong>Class Component:</strong> LEC</li>
     *      Result of the method returns: "LEC".
     * @param aClass The DOM element that contains a child node.
     * @return The trimmed text from the first child node.
     */
    private static String getFirstChildNodeAndReturnAsString(Elements aClass) {
        return aClass
                .stream()
                .findFirst()
                .map(e1 -> e1.childNode(1))
                .map(Node::toString)
                .map(String::trim)
                .get();
    }

    /**
     * Retrieves the requested DOM element, as specified by the {@param htmlElements} HTML Element.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @param htmlElement The requested DOM element from the entire class.
     * @return The String representation of the information inside the requested DOM element.
     */
    public static String getClassFromElementUsingHTMLElement(Element aClass, HTMLElements htmlElement) {
        return Optional
                .ofNullable(aClass.select(htmlElement.getHtml()))
                .orElse(new Elements(1))
                .text();
    }

    /**
     * Retrieves the requested start/end class time for a class, given class days and times.
     *
     * @param classDaysTimes The days and times, if it exists, for a class.
     *                       The parameter is in the format of "SunMoTuWeThFrSa HH:MM A.M.-HH:MM P.M.".
     * @param isFirstHalf Condition that determines whether to retrieve the first half of the String (true),
     *                    or the second half of the String (false).
     * @return The start or end class time for a class, in the format of "HH:MM A.M.".
     */
    private static String getClassTime(Optional<String> classDaysTimes, boolean isFirstHalf) {
        return classDaysTimes
                .filter(IS_NOT_EMPTY)
                .filter(IS_NOT_EMPTY_STRING)
                .filter(IS_NOT_STRING_WITH_TWO_HYPHENS)
                .filter(IS_NOT_STRING_WITH_ONE_HYPHEN)
                .map(e -> {
                    String wholeString = e.substring(e.indexOf(" "));
                    return splitByHyphenAndExtractHalf(wholeString, isFirstHalf);
                })
                .orElse(EMPTY_STRING);
    }

    /**
     * Retrieves the class days and times from the DOM and returns the requested start/end class time for a class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @param isFirstHalf Condition that determines whether to retrieve the first half of the String (true),
     *                    or the second half of the String (false).
     * @return The start or end class time for a class, in the format of "HH:MM A.M.".
     */
    private static String getRequestedClassTime(Element aClass, boolean isFirstHalf) {
        Optional<String> classTimesFromHtml = Optional.ofNullable(getClassDaysAndTimes(aClass));
        Optional<String> time = Optional.ofNullable(getClassTime(classTimesFromHtml, isFirstHalf));
        return time
                .filter(IS_NOT_EMPTY)
                .orElse(EMPTY_STRING);
    }

    /**
     * Retrieves the end time for a class, in the format of "HH:MM A.M.".
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The end time for a class.
     */
    public static String getClassEndTime(Element aClass) {
        return getRequestedClassTime(aClass, false);
    }

    /**
     * Retrieves the start time for a class, in the format of "HH:MM A.M.".
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return The start time for a class.
     */
    public static String getClassStartTime(Element aClass) {
        return getRequestedClassTime(aClass, true);
    }

    /**
     * Determines whether a class falls on a certain day, given the class information DOM.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @param classDayAbbreviation The day abbreviation that the class falls on. The only acceptable days are:
     *                             "Mo" -> Monday, "Tu" -> Tuesday, "We" -> Wednesday, "Th" -> Thursday,
     *                             "Fr" -> Friday, "Sa" -> Saturday, "Sun" -> Sunday
     * @return A boolean denoting whether the class falls under the class day or not.
     */
    private static boolean isClassOnCertainDay(Element aClass, String classDayAbbreviation) {
        String classDays = getClassDaysAndTimes(aClass);
        return classDays.contains(classDayAbbreviation);
    }

    /**
     * Determines whether a class falls on Monday, or not, given the class information DOM.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return A boolean denoting whether the class falls on Monday or not.
     */
    public static boolean isMondayClass(Element aClass) {
        return isClassOnCertainDay(aClass, MONDAY_ABBREVIATION);
    }

    /**
     * Determines whether a class falls on Tuesday, or not, given the class information DOM.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return A boolean denoting whether the class falls on Tuesday or not.
     */
    public static boolean isTuesdayClass(Element aClass) {
        return isClassOnCertainDay(aClass, TUESDAY_ABBREVIATION);
    }

    /**
     * Determines whether a class falls on Wednesday, or not, given the class information DOM.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return A boolean denoting whether the class falls on Wednesday or not.
     */
    public static boolean isWednesdayClass(Element aClass) {
        return isClassOnCertainDay(aClass, WEDNESDAY_ABBREVIATION);
    }

    /**
     * Determines whether a class falls on Thursday, or not, given the class information DOM.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return A boolean denoting whether the class falls on Thursday or not.
     */
    public static boolean isThursdayClass(Element aClass) {
        return isClassOnCertainDay(aClass, THURSDAY_ABBREVIATION);
    }

    /**
     * Determines whether a class falls on Friday, or not, given the class information DOM.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return A boolean denoting whether the class falls on Friday or not.
     */
    public static boolean isFridayClass(Element aClass) {
        return isClassOnCertainDay(aClass, FRIDAY_ABBREVIATION);
    }

    /**
     * Determines whether a class falls on Saturday, or not, given the class information DOM.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return A boolean denoting whether the class falls on Saturday or not.
     */
    public static boolean isSaturdayClass(Element aClass) {
        return isClassOnCertainDay(aClass, SATURDAY_ABBREVIATION);
    }

    /**
     * Determines whether a class falls on Sunday, or not, given the class information DOM.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return A boolean denoting whether the class falls on Sunday or not.
     */
    public static boolean isSundayClass(Element aClass) {
        return isClassOnCertainDay(aClass, SUNDAY_ABBREVIATION);
    }

    /**
     * Retrieves the department course number, in the format of "1234", from the class name and CRN.
     * @param classNameAndCRN The class name and CRN.
     * @return The department course number.
     */
    public static String getDepartmentCourseNumber(String classNameAndCRN) {
        return StringUtil.splitBySpaceAndExtractHalf(classNameAndCRN, false);
    }

    /**
     * Retrieves the department abbreviation, in the format of "ABCD",
     * of the department the class is associated with.
     * @param classNameAndCRN The class name and CRN.
     * @return The department abbreviation.
     */
    public static String getDepartmentAbbreviation(String classNameAndCRN) {
        return StringUtil.splitBySpaceAndExtractHalf(classNameAndCRN, true);
    }

    /**
     * Retrieves the number of classes for a given term.
     *
     * @param doc The entire web page of the class browser.
     * @return The number of classes that will be scraped for the term.
     */
    public static int getNumberOfClasses(Document doc) {
        Elements allClasses = doc.select(HTMLElements.NUMBER_OF_CLASSES.getHtml());
        return IntegerUtil.parseInt(extractTextBetweenParentheses(allClasses));
    }

    /**
     * Gets a Class object from the DOM element that contains information about an entire class.
     *
     * @param aClass The DOM element that contains information about an entire class.
     * @return A Class object that represents the scraped information from the DOM entry for a single class.
     */
    public static Class getClassFromAClassElement(Element aClass) {
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
