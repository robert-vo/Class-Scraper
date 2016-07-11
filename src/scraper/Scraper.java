package scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ui.ScraperGUI;

import java.io.IOException;

public class Scraper {

    final static private String REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES = "[\\(\\)]";
    private int numberOfClasses;
    final private Document WEBSITE_TO_SCRAPE;

    public Scraper(Term term) throws IOException {
        WEBSITE_TO_SCRAPE = generateDocumentForTerm(term);
    }

    public Document getWebsiteToScrape() {
        return WEBSITE_TO_SCRAPE;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public void startScraper(Term term) throws IOException {
        numberOfClasses = getNumberOfClasses(WEBSITE_TO_SCRAPE);
        ScraperGUI.appendToLoggerTextArea("Processing " + numberOfClasses + " classes.");
    }

    public static int getNumberOfClasses(Document document) {
        try {
            return Integer.parseInt(document.select(HTMLElements.NUMBER_OF_CLASSES.getHtml())
                    .first()
                    .text()
                    .split(REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES)[1]);
        }
        catch (Exception e) {
            return 0;
        }
    }

    private Document generateDocumentForTerm(Term term) throws IOException {
        String url = URLBuilder.createURLForTermOnly(term);
        return Jsoup.connect(url).get();
    }

    public static boolean verifyValidDocument(Document document) {
        Elements allClasses = document.select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml());
        return allClasses.size() != 0;
    }

    public Class getAllClassInformationFromDocument(Document document) {

        return new Class();
    }

    public String retrieveClassName(Document document) {
        return "Class";
    }

}
