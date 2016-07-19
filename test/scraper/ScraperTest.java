package scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static scraper.ScraperRunner.getNumberOfClasses;
import static scraper.ScraperRunner.verifyValidDocument;

public class ScraperTest {

    private Document returnDocumentFromFileName(String fileName) throws IOException {
        File page = new File("test/resources/" + fileName + ".html");
        return Jsoup.parse(page, "UTF-8", "");
    }

    @Test
    public void canaryTest() {
        assertTrue(true);
    }

    @Test
    public void testValidDocumentReturnsFalseForEmptyPage() throws IOException {
        Document document = returnDocumentFromFileName("empty");
        assertFalse(verifyValidDocument(document));
    }

    @Test
    public void testValidDocumentReturnsTrueForValidPage() throws IOException {
        Document document = returnDocumentFromFileName("coscPageOne");
        assertTrue(verifyValidDocument(document));
    }

    @Test
    public void testValidDocumentReturnsFalseForInvalidPage() throws IOException {
        Document document = returnDocumentFromFileName("validPageWithNoClasses");
        assertFalse(verifyValidDocument(document));
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

}
