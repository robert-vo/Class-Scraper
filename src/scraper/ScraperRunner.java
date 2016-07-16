package scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ui.ScraperGUI;
import java.io.File;
import java.io.IOException;

import static scraper.ScrapeElements.*;

public class ScraperRunner implements Scraper {

    private int numberOfClasses;
    private static String WEBSITE_URL;
    private Document WEBSITE_TO_SCRAPE;

    public ScraperRunner(Term term) {
        WEBSITE_URL = URLBuilder.createURLForTermOnly(term);
    }

    public Document getWebsiteToScrape() {
        return WEBSITE_TO_SCRAPE;
    }

    public static int getNumberOfClasses(Document document) {
        Elements numberOfClassesInHTML = document.select(HTMLElements.NUMBER_OF_CLASSES.getHtml());
        return Integer.parseInt(Scraper.extractTextBetweenParentheses(numberOfClassesInHTML));
    }

    public Document generateDocumentForTerm(Term term) throws IOException {
        String url = URLBuilder.createURLForTermOnly(term);
        return Jsoup.connect(url).get();
    }

    public static boolean verifyValidDocument(Document document) {
        Elements allClasses = document.select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml());
        return allClasses.size() != 0;
    }

    public static Document retrieveWebpage(String URL) throws IOException {
        Connection.Response response = Jsoup.connect(URL)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .referrer("http://www.google.com")
                .timeout(12000)
                .followRedirects(true)
                .execute();
        return response.parse();
    }

    public static void main(String[] args) throws IOException {


//        System.out.println(ScraperRunner.getNumberOfClasses(entireWebpage));
//
//        ScraperGUI.appendToLoggerTextArea("Processing " + ScraperRunner.getNumberOfClasses(entireWebpage) + " classes.");

    }

    public static void run() throws IOException {
        Document entireWebPage = retrieveWebpage(WEBSITE_URL);
    }

}
