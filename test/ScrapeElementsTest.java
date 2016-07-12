import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import scraper.HTMLElements;
import scraper.ScrapeElements;
import scraper.Scraper;
import scraper.Term;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScrapeElementsTest {

    Scraper mock = mock(Scraper.class);
    Element aClass;
    final private String NAME_COURSE_NUMBER = "COSC 1300 (15240)";
    final private String COURSE_TITLE       = "Introduction To Computing";

    @Before
    public void setUp() throws IOException {
        when(mock.generateDocumentForTerm(Term.FALL_2015)).thenReturn(returnDocumentFromFileName("coscPageOne"));
        aClass = mock.generateDocumentForTerm(Term.FALL_2015)
                     .select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml())
                     .get(0);
    }

    @Test
    public void testGetNameAndCourseNumberForCosc1300() {
        assertEquals(ScrapeElements.getNameAndCourseNumber(aClass), NAME_COURSE_NUMBER);
    }

    @Test
    public void testGetCourseTitleIntroToComputing() {
        assertEquals(ScrapeElements.getCourseTitle(aClass), COURSE_TITLE);
    }

    private Document returnDocumentFromFileName(String fileName) throws IOException {
        File page = new File("test/resources/" + fileName + ".html");
        return Jsoup.parse(page, "UTF-8", "");
    }

}
