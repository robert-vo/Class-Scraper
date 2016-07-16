package scraper;

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
    final private Document WEBSITE_TO_SCRAPE;

    public ScraperRunner(Term term) throws IOException {
        WEBSITE_TO_SCRAPE = generateDocumentForTerm(term);
    }

    public Document getWebsiteToScrape() {
        return WEBSITE_TO_SCRAPE;
    }

    public void startScraper(Term term) throws IOException {
        numberOfClasses = getNumberOfClasses(WEBSITE_TO_SCRAPE);
        ScraperGUI.appendToLoggerTextArea("Processing " + numberOfClasses + " classes.");
    }

    public static int getNumberOfClasses(Document document) {
        Elements numberOfClassesInHTML = document.select(HTMLElements.CLASS_NAME_AND_CRN_NUMBER.getHtml());
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

    public Class getAllClassInformationFromDocument(Document document) {

        return new Class();
    }

    public static void getASingleClassInformation() throws IOException {
        File input = new File("test/resources/coscPageOne.html");
        Document doc = Jsoup.parse(input, "UTF-8", "");
        Elements allClasses = doc.select("ul[id=accordion] > li");

        System.out.println(getNameAndCourseNumber(allClasses.get(0)));
        System.out.println(getCourseTitle(allClasses.get(0)));
        System.out.println(getCourseStatus(allClasses.get(0)));
        System.out.println(getClassAttributes(allClasses.get(0)));
    }

    public static void main(String[] args) throws IOException {
        getASingleClassInformation();
    }



}
