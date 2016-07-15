import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import scraper.HTMLElements;
import scraper.ScraperRunner;
import scraper.Term;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scraper.ScrapeElements.*;

public class ScrapeElementsTest {

    ScraperRunner mock = mock(ScraperRunner.class);
    Element aClass;
    final private String NAME_COURSE_NUMBER = "COSC 1300 (15240)";
    final private String COURSE_TITLE       = "Introduction To Computing";
    final private String COURSE_STATUS      = "Open (12/35)";
    final private String CLASS_DATES        = "Aug 22, 2016 – Dec 15, 2016";
    final private String CLASS_ATTRIBUTES   = "No class attributes";
    final private String CLASS_DAYS_TIMES   = "MoWe 04:00 P.M.-05:30 P.M.";
    final private String INSTRUCTOR_NAME    = "Robert Lea";
    final private String INSTRUCTOR_EMAIL   = "rlea@uh.edu";
    final private String LOCATION           = "UH";
    final private String FORMAT             = "Face to Face";
    final private String ROOM               = "C 111";
    final private String DESCRIPTION        = "Cr. 3. (2-3). May not be used to satisfy any computer science degree requirement. An introduction to computing with emphasis on the practical usage of personal computers; concepts of recorded programs, computer organization, operating systems, mainframes, minicomputers, and personal computer; selected applications of personal computers; word processing, databases, and spreadsheets; societal impact of computers.";
    final private String COURSE_NUMBER      = "15240";
    final private String SESSION            = "Regular Academic Session";
    final private String SYLLABUS           = "Unavailable";
    final private String LAST_DAY_TO_ADD    = "No information on last day to add found yet";
    final private String CLASS_DURATION     = "15 weeks";
    final private String CLASS_COMPONENT    = "LEC";
    final private String LAST_DATE_TO_DROP  = "No information on last day to drop found yet";

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
    public void testGetCourseTitleIntroToComputing() {
        assertEquals(getCourseTitle(aClass), COURSE_TITLE);
    }

    @Test
    public void testGetCourseStatusOpen() {
        assertEquals(getCourseStatus(aClass), COURSE_STATUS);
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
    public void testGetCourseNumberFromAClass() {
        assertEquals(getCourseNumber(aClass), COURSE_NUMBER);
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

    private Document returnDocumentFromFileName(String fileName) throws IOException {
        File page = new File("test/resources/" + fileName + ".html");
        return Jsoup.parse(page, "UTF-8", "");
    }

}
