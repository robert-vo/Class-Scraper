package scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ui.ScraperGUI;
import java.io.IOException;
import java.util.List;

public class ScraperRunner implements Scraper {

    private static String WEBSITE_URL;
    private Document WEBSITE_TO_SCRAPE;
    private static Term term;

    public ScraperRunner(Term term) {
        WEBSITE_URL = URLBuilder.createURLForTermOnly(term);
        this.term = term;
    }

    public static Term getTerm() {
        return term;
    }

    public Document getWebsiteToScrape() throws IOException {
        WEBSITE_TO_SCRAPE = retrieveWebpage(WEBSITE_URL);
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
        try {
            Elements allClasses = document.select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml());
            return allClasses.size() != 0;
        }
        catch (NullPointerException e) {
            return false;
        }
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

    public static void run() throws IOException {
        try {

            Document entireWebPage = retrieveWebpage(WEBSITE_URL);

            while (verifyValidDocument(entireWebPage)) {
                Elements allClasses = entireWebPage.select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml());
                DatabaseOperations.performDatabaseActions(allClasses);
                WEBSITE_URL = URLBuilder.incrementPageNumber(WEBSITE_URL);
                entireWebPage = retrieveWebpage(WEBSITE_URL);
                ScraperGUI.appendToLoggerTextArea("Starting next page: " + WEBSITE_URL);
            }

            ScraperGUI.appendToLoggerTextArea("Finished Scraping");
        }
        catch (Exception e1) {
            ScraperGUI.appendToLoggerTextArea(e1.getMessage());
        }
    }

    public static List<Class> convertDocumentToClass(Document e) {
        e.select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml()).stream().map(e -> e).
    }


}
