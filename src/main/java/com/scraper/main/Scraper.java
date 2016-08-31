package com.scraper.main;

import org.jsoup.nodes.Document;

import java.lang.*;
import java.util.List;

/**
 * The Scraper interface defines what methods an inherited class will have to implement.
 *
 * @author Robert Vo
 */
public interface Scraper {

    String USER_AGENT_STRING    = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    String REFERRAL_URL         = "http://www.google.com";

    void startScraper();
    void startScraperForMultipleTerms();
    void setWebSiteFromTerm();
    void retrieveWebPage();
    void scrapeCurrentPageAndReturnAsListOfClass();
    void advanceToNextPage();
    void setTerm(Term term);
    void setCurrentWebSiteDocument(Document doc);
    void scrapeAndRetrieveAllClasses();
    int getNumberOfClasses();
    boolean isValidWebSiteWithClasses();
    List<Class> getAllClasses();
    Term getTerm();
    void setPageLimit(int pageLimit);
}
