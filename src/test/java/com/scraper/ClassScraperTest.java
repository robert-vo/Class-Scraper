package com.scraper;

import com.scraper.main.ClassScraper;
import com.scraper.main.pojo.Term;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static com.scraper.util.DocumentUtility.returnDocumentFromFileName;

public class ClassScraperTest {

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
        classScraper.setCurrentWebSiteDocument(returnDocumentFromFileName("empty"));
        assertFalse(classScraper.isValidWebSiteWithClasses());
    }

    @Test
    public void testValidDocumentReturnsTrueForValidPage() throws IOException {
        classScraper.setCurrentWebSiteDocument(returnDocumentFromFileName("coscPageOne"));
        assertTrue(classScraper.isValidWebSiteWithClasses());
    }

    @Test
    public void testValidDocumentReturnsFalseForInvalidPage() throws IOException {
        classScraper.setCurrentWebSiteDocument(returnDocumentFromFileName("validPageWithNoClasses"));
        assertFalse(classScraper.isValidWebSiteWithClasses());
    }

    @Test
    public void testSetWebSiteFromTermWorks() {
        classScraper.setWebSiteURLFromTerm();
        classScraper.advanceToNextPage();
        assertEquals(classScraper.getWebsiteURL(), "http://classbrowser.uh.edu/classes?term=2000&page=2");
    }

    @Test(expected = NullPointerException.class)
    public void testAdvanceToNextPageForInvalidTerm() {
        classScraper.setTerm(null);
        classScraper.advanceToNextPage();
    }

    @Test(expected = NullPointerException.class)
    public void testSetWebSiteForInvalidTerm() {
        classScraper.setTerm(null);
        classScraper.setWebSiteURLFromTerm();
    }

}
