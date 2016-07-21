package scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ui.ScraperGUI;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ClassScraper implements Scraper {

    Term        term;
    String      websiteURL;
    Document    currentWebSiteDocument;
    List<Class> allClassesForAGivenDocument;

    public ClassScraper(Term term) {
        this.term = term;
    }

    @Override
    public void startScraper() {
        print("Starting Scraper");
        setWebSiteFromTerm();
        retrieveWebPage();
        print("Retrieved the following website: " + websiteURL);
        try {
            if(isValidWebSiteWithClasses()) {
                print("Starting scraper for " + getNumberOfClasses() + " classes.");
                do {
                    scrapeCurrentPageAndReturnAsListOfClass();
                    DatabaseOperations.performDatabaseActions(allClassesForAGivenDocument);
                    print("Modified the database with the scraped classes. Advancing to the next page.");
                    advanceToNextPage();
                    retrieveWebPage();
                    print("Retrieved the following website: " + websiteURL);
                } while(isValidWebSiteWithClasses());
                print("Scraping finished. Please check the database to see the results of the scraping.");
            }
            else {
                print("Invalid website. Stopping scraper.");
            }
        }
        catch (Exception e) {
            print(e.getMessage());
        }
    }

    @Override
    public void setWebSiteFromTerm() {
        if(term.getTermID().length() > 0) {
            websiteURL = URLBuilder.createURLForTermOnly(term);
        }
        else {
            print("Invalid term. Please validate that the term is set.");
        }
    }

    @Override
    public void setTerm(Term term) {
        this.term = term;
    }

    @Override
    public boolean isValidWebSiteWithClasses() {
        try {
            Elements allClasses = currentWebSiteDocument.select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml());
            return allClasses.size() != 0;
        }
        catch (NullPointerException e) {
            print("Invalid website. Please validate that the term is set.");
            return false;
        }
    }

    @Override
    public void retrieveWebPage() {
        try {
            Connection.Response response = Jsoup.connect(websiteURL)
                .ignoreContentType(true)
                .userAgent(USER_AGENT_STRING)
                .referrer(REFFERAL_URL)
                .timeout(12000)
                .followRedirects(true)
                .execute();
            currentWebSiteDocument = response.parse();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void scrapeCurrentPageAndReturnAsListOfClass() {
        allClassesForAGivenDocument = currentWebSiteDocument
                .select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml())
                .stream()
                .map(ScrapeElements::convertElementToAClass)
                .collect(Collectors.toList());
    }

    @Override
    public void advanceToNextPage() {
        websiteURL = URLBuilder.incrementPageNumber(websiteURL);
    }

    @Override
    public void print(String message) {
        ScraperGUI.appendToLoggerTextArea(message);
    }

    @Override
    public int getNumberOfClasses() {
        return ScrapeElements.getNumberOfClasses(currentWebSiteDocument);
    }


}