package scraper;

public interface Scraper {

    String USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    String REFFERAL_URL = "http://www.google.com";

    void startScraper();
    void setWebSiteFromTerm();
    void retrieveWebPage();
    void scrapeCurrentPageAndReturnAsListOfClass();
    void advanceToNextPage();
    void setTerm(Term term);
    void print(String message);
    int getNumberOfClasses();
    boolean isValidWebSiteWithClasses();

}
