package com.scraper.main;

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
    void setWebSiteURLFromTerm();
    void retrieveAndSetWebPage();
    void scrapeCurrentPageAndAddToListOfClass();
    void advanceToNextPage();
    void scrapeEachWebPageAndAddToListOfClass();
    int getNumberOfClasses();
    boolean isValidWebSiteWithClasses();
    void setPageLimit(int pageLimit);

}
