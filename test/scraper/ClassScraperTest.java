package scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ClassScraperTest {

    private Document returnDocumentFromFileName(String fileName) throws IOException {
        File page = new File("test/resources/" + fileName + ".html");
        return Jsoup.parse(page, "UTF-8", "");
    }

    ClassScraper classScraper;

    @Before
    public void setUp() throws IOException {
        classScraper = new ClassScraper(Term.FALL_2016);
    }

    @Test
    public void canaryTest() {
        assertTrue(true);
    }

    @Test
    public void testValidDocumentReturnsFalseForEmptyPage() throws IOException {
        classScraper.currentWebSiteDocument = returnDocumentFromFileName("empty");
        assertFalse(classScraper.isValidWebSiteWithClasses());
    }

    @Test
    public void testValidDocumentReturnsTrueForValidPage() throws IOException {
        classScraper.currentWebSiteDocument = returnDocumentFromFileName("coscPageOne");
        assertTrue(classScraper.isValidWebSiteWithClasses());
    }

    @Test
    public void testValidDocumentReturnsFalseForInvalidPage() throws IOException {
        classScraper.currentWebSiteDocument = returnDocumentFromFileName("validPageWithNoClasses");
        assertFalse(classScraper.isValidWebSiteWithClasses());
    }

    @Test
    public void testSetWebSiteFromTermWorks() {
        classScraper.setWebSiteFromTerm();
        classScraper.advanceToNextPage();
        assertEquals(classScraper.websiteURL, "http://classbrowser.uh.edu/classes?term=2000&page=2");
    }

    @Test(expected = NullPointerException.class)
    public void testAdvanceToNextPageForInvalidTerm() {
        classScraper.setTerm(null);
        classScraper.advanceToNextPage();
    }

    @Test(expected = NullPointerException.class)
    public void testSetWebSiteForInvalidTerm() {
        classScraper.setTerm(null);
        classScraper.setWebSiteFromTerm();
    }

}
