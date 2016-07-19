import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import scraper.Class;
import scraper.HTMLElements;
import scraper.ScraperRunner;
import scraper.Term;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scraper.ScrapeElements.*;

public class ScrapeElementsTest {

    ScraperRunner mock = mock(ScraperRunner.class);
    Element aClass;
    final private String NAME_COURSE_NUMBER     = "COSC 1300 (15240)";
    final private String COURSE_NAME            = "COSC 1300";
    final private String COURSE_NUMBER          = "15240";
    final private String COURSE_TITLE           = "Introduction To Computing";
    final private String COURSE_STATUS_SEATS    = "Open (12/35)";
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
        when(mock.generateDocumentForTerm(Term.FALL_2016)).thenReturn(returnDocumentFromFileName("coscPageOne"));
        aClass = mock.generateDocumentForTerm(Term.FALL_2016)
                     .select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml())
                     .get(0);
    }

    @Test
    public void testGetNameAndCourseNumberForCosc1300() {
        assertEquals(getNameAndCourseNumber(aClass), NAME_COURSE_NUMBER);
    }

    @Test
    public void testGetCourseName() {
        assertEquals(getCourseName(aClass), COURSE_NAME);
    }

    @Test
    public void testGetCourseNumberFromAClass() {
        assertEquals(getCourseNumber(aClass), COURSE_NUMBER);
    }

    @Test
    public void testGetCourseTitleIntroToComputing() {
        assertEquals(getCourseTitle(aClass), COURSE_TITLE);
    }

    @Test
    public void testGetCourseStatusOpenAndSeats() {
        assertEquals(getCourseStatusAndSeats(aClass), COURSE_STATUS_SEATS);
    }

    @Test
    public void testGetcourseStatusOpen() {
        assertEquals(getCourseStatusOpenOrClosed(aClass), Class.Status.Open);
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
        assertEquals(getLocation(aClass), LOCATION);
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
    public void testDocumentHasTenClasses() throws IOException {
        assertEquals(mock.generateDocumentForTerm(Term.FALL_2016)
                         .select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml())
                         .size(), 10);
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

        Elements allClasses = mock.generateDocumentForTerm(Term.FALL_2016)
                                  .select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml());

        ArrayList<String> listOfScrapedNames = allClasses.stream()
                                                         .map(e -> e.select(HTMLElements.COURSE_TITLE.getHtml()).text())
                                                         .collect(Collectors.toCollection(ArrayList::new));

        assertEquals(listOfClassNames, listOfScrapedNames);
    }

    private Document returnDocumentFromFileName(String fileName) throws IOException {
        File page = new File("test/resources/" + fileName + ".html");
        return Jsoup.parse(page, "UTF-8", "");
    }

}
