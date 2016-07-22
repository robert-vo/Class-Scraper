package main.java.scraper;

import org.jsoup.nodes.Document;

import java.util.List;

public interface Scraper {

    String USER_AGENT_STRING    = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    String REFERRAL_URL         = "http://www.google.com";

    void startScraper();
    void setWebSiteFromTerm();
    void retrieveWebPage();
    void scrapeCurrentPageAndReturnAsListOfClass();
    void advanceToNextPage();
    void setTerm(Term term);
    void print(String message);
    void setCurrentWebSiteDocument(Document doc);
    void retrieveAllClasses();
    int getNumberOfClasses();
    boolean isValidWebSiteWithClasses();
    List<Class> getAllClasses();
    Term getTerm();

}
