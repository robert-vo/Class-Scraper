package com.scraper;

import com.scraper.main.Class;
import com.scraper.main.ClassScraper;
import com.scraper.main.HTMLElements;
import com.scraper.main.Term;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.scraper.main.ScrapeHTMLElements.*;
import static com.scraper.util.DocumentUtility.returnDocumentFromFileName;
import static org.junit.Assert.*;

public class ScrapeHTMLElementsTest {

    ClassScraper classScraper = new ClassScraper(Term.FALL_2016);
    Element aClass;

    final private String NAME_CLASS_NUMBER      = "COSC 1300 (15240)";
    final private String CLASS_NAME             = "COSC 1300";
    final private String DEPARTMENT_NAME        = "COSC";
    final private String DEPARTMENT_COURSE_NUM  = "1300";
    final private String CLASS_NUMBER           = "15240";
    final private String CLASS_TITLE            = "Introduction To Computing";
    final private String CLASS_STATUS_SEATS     = "Open (12/35)";
    final private String CLASS_DATES            = "Aug 22, 2016 – Dec 15, 2016";
    final private String CLASS_START_DATE       = "Aug 22, 2016";
    final private String CLASS_END_DATE         = "Dec 15, 2016";
    final private String CLASS_ATTRIBUTES       = "No class attributes";
    final private String CLASS_DAYS_TIMES       = "MoWe 04:00 P.M.-05:30 P.M.";
    final private String INSTRUCTOR_NAME        = "Robert Lea";
    final private String INSTRUCTOR_EMAIL       = "rlea@uh.edu";
    final private String LOCATION               = "UH";
    final private String FORMAT                 = "Face to Face";
    final private String ROOM                   = "C 111";
    final private String DESCRIPTION            = "Cr. 3. (2-3). May not be used to satisfy any computer science degree requirement. An introduction to computing with emphasis on the practical usage of personal computers; concepts of recorded programs, computer organization, operating systems, mainframes, minicomputers, and personal computer; selected applications of personal computers; word processing, databases, and spreadsheets; societal impact of computers.";
    final private String SESSION                = "Regular Academic Session";
    final private String SYLLABUS               = "Unavailable";
    final private String CLASS_DURATION         = "15 weeks";
    final private String CLASS_COMPONENT        = "LEC";

    @Before
    public void setUp() throws IOException {
        classScraper.setCurrentWebSiteDocument(returnDocumentFromFileName("coscPageOne"));
        aClass = classScraper.getCurrentWebSiteDocument()
                .select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml())
                .get(0);
    }

    @Test
    public void testReturnNumberOfClasses68ForValidPage() throws IOException {
        Document document = returnDocumentFromFileName("coscPageOne");
        assertEquals(getNumberOfClasses(document), 68);
    }

    @Test
    public void testReturnNumberOfClasses37ForValidPage() throws IOException {
        Document document = returnDocumentFromFileName("validPageWithNoClasses");
        assertEquals(getNumberOfClasses(document), 37);
    }

    @Test
    public void testReturnNumberOfClasses0ForValidPage() throws IOException {
        Document document = returnDocumentFromFileName("empty");
        assertEquals(getNumberOfClasses(document), 0);
    }

    @Test
    public void testGetNameAndClassNumberForCosc1300() {
        assertEquals(getClassSubjectNumberCRN(aClass), NAME_CLASS_NUMBER);
    }

    @Test
    public void testGetClassName() {
        assertEquals(getClassSubject(aClass), CLASS_NAME);
    }

    @Test
    public void testGetDepartmentCoscFromClassName() {
        assertEquals(getDepartmentAbbreviation(CLASS_NAME), DEPARTMENT_NAME);
    }

    @Test
    public void testGetDepartmentClassNumber1300FromClassName() {
        assertEquals(getDepartmentCourseNumber(CLASS_NAME), DEPARTMENT_COURSE_NUM);
    }

    @Test
    public void testGetDepartmentABCDFromClassName() {
        assertEquals(getDepartmentAbbreviation("ABCD 1234"), "ABCD");
    }

    @Test
    public void testGetDepartmentClassNumber1234FromClassName() {
        assertEquals(getDepartmentCourseNumber("ABCD 1234"), "1234");
    }

    @Test
    public void testGetDepartmentXYZFromClassName() {
        assertEquals(getDepartmentAbbreviation("XYZ 1234"), "XYZ");
    }

    @Test
    public void testGetDepartmentClassNumber123FromClassName() {
        assertEquals(getDepartmentCourseNumber("XYZ 123"), "123");
    }

    @Test
    public void testGetClassNumberFromAClass() {
        assertEquals(getClassCRN(aClass), CLASS_NUMBER);
    }

    @Test
    public void testGetClassTitleIntroToComputing() {
        assertEquals(getClassTitle(aClass), CLASS_TITLE);
    }

    @Test
    public void testGetClassStatusOpenAndSeats() {
        assertEquals(getClassStatusAndSeats(aClass), CLASS_STATUS_SEATS);
    }

    @Test
    public void testGetClassStatusOpen() {
        assertEquals(getClassStatusOpenOrClosed(aClass), Class.Status.Open);
    }

    @Test
    public void testGetNumberOfSeatsTaken12() {
        assertEquals(getNumberOfSeatsTaken(aClass), 12);
    }

    @Test
    public void testGetNumberOfTotalSeats35() {
        assertEquals(getNumberOfTotalSeats(aClass), 35);
    }

    @Test
    public void testGetNoAttributesFromAClass() {
        assertEquals(getClassAttributes(aClass), CLASS_ATTRIBUTES);
    }

    @Test
    public void testGetClassDatesFromAClass() {
        assertEquals(getClassDates(aClass), CLASS_DATES);
    }

    @Test
    public void testGetStartDateFromAClass() {
        assertEquals(getClassStartDate(aClass), CLASS_START_DATE);
    }

    @Test
    public void testGetEndDateFromAClass() {
        assertEquals(getClassEndDate(aClass), CLASS_END_DATE);
    }

    @Test
    public void testGetClassDaysAndTimesFromAClass() {
        assertEquals(getClassDaysAndTimes(aClass), CLASS_DAYS_TIMES);
    }

    @Test
    public void testGetInstructorNameFromAClass() {
        assertEquals(getInstructorName(aClass), INSTRUCTOR_NAME);
    }

    @Test
    public void testGetInstructorEmailFromAClass() {
        assertEquals(getInstructorEmail(aClass), INSTRUCTOR_EMAIL);
    }

    @Test
    public void testGetLocationFromAClass() {
        assertEquals(getCampusLocation(aClass), LOCATION);
    }

    @Test
    public void testGetRoomFromAClass() {
        assertEquals(getRoom(aClass), ROOM);
    }

    @Test
    public void testGetFormatFromAClass() {
        assertEquals(getFormat(aClass), FORMAT);
    }

    @Test
    public void testGetDescriptionFromAClass() {
        assertEquals(getDescription(aClass), DESCRIPTION);
    }


    @Test
    public void testGetSessionFromAClass() {
        assertEquals(getSession(aClass), SESSION);
    }

    @Test
    public void testGetSyllabusFromAClass() {
        assertEquals(getSyllabus(aClass), SYLLABUS);
    }

    @Test
    public void testGetClassDurationFromAClass() {
        assertEquals(getClassDuration(aClass), CLASS_DURATION);
    }

    @Test
    public void testGetClassComponentFromAClass() {
        assertEquals(getClassComponent(aClass), CLASS_COMPONENT);
    }

    @Test
    public void testDocumentHas68Classes() throws IOException {
        assertEquals(classScraper.getNumberOfClasses(), 68);
    }

    @Test
    public void testGetClassStartTime() throws Exception {
        assertEquals(getClassStartTime(aClass), "04:00 P.M.");
    }

    @Test
    public void testGetClassEndTime() throws Exception {
        assertEquals(getClassEndTime(aClass), "05:30 P.M.");
    }

    @Test
    public void testIsMondayClass() throws Exception {
        assertTrue(isMondayClass(aClass));
    }

    @Test
    public void testIsTuesdayClass() throws Exception {
        assertFalse(isTuesdayClass(aClass));
    }

    @Test
    public void testIsWednesdayClass() throws Exception {
        assertTrue(isWednesdayClass(aClass));
    }

    @Test
    public void testIsThursdayClass() throws Exception {
        assertFalse(isThursdayClass(aClass));
    }

    @Test
    public void testIsFridayClass() throws Exception {
        assertFalse(isFridayClass(aClass));
    }

    @Test
    public void testIsSaturdayClass() throws Exception {
        assertFalse(isSaturdayClass(aClass));
    }

    @Test
    public void testIsSundayClass() throws Exception {
        assertFalse(isSundayClass(aClass));
    }

    @Test
    public void testScrapeDocumentHasCorrectNames() throws IOException {
        final ArrayList<String> listOfClassNames = new ArrayList<String>() {{
            add("Introduction To Computing");
            add("C Programming");
            add("Computer Science & Program");
            add("Computer Science & Program");
            add("Computer Science & Program");
            add("Intro Computer Science II");
            add("Intro Computer Science II");
            add("Introduction to Programming");
            add("Introduction to Programming");
            add("Introduction to Programming");
        }};

        Elements allClasses = classScraper.getCurrentWebSiteDocument()
                .select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml());

        ArrayList<String> listOfScrapedNames =
                allClasses.stream()
                        .map(e -> e.select(HTMLElements.CLASS_TITLE.getHtml()).text())
                        .collect(Collectors.toCollection(ArrayList::new));

        assertEquals(listOfClassNames, listOfScrapedNames);
    }
}
