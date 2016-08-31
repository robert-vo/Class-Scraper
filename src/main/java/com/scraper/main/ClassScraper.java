package com.scraper.main;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * ClassScraper contains methods that will scrape classes on the
 * University of Houston's class browser and stores the classes in memory.
 *
 * @author Robert Vo
 */
public class ClassScraper implements Scraper {

    private Term                term;
    private List<Term>          terms;
    private String              websiteURL;
    private Document            currentWebSiteDocument;
    private List<Class>         allClassesForAGivenDocument;
    private List<Class>         allClasses  = new ArrayList<>();
    private int                 pageLimit   = Integer.MAX_VALUE;
    private static Logger log = Logger.getLogger(ClassScraper.class);

    public ClassScraper(Term term) {
        this.term = term;
        log.info("Initialized ClassScraper with term " + term.toString());
    }

    public ClassScraper(int year, String semester) {
        String termValue = semester.toUpperCase() + "_" + year;
        this.term = Term.valueOf(termValue);
        log.info("Initialized ClassScraper with term " + term.toString());
    }

    public ClassScraper(List<Term> terms) {
        this.terms = terms;
        log.info("Initialized ClassScraper with terms " + terms.toString());
    }

    @Override
    public void setPageLimit(int pageLimit) {
        log.info("The scraper will only run for " + pageLimit + " pages.");
        this.pageLimit = pageLimit;
    }

    @Override
    public void setTerm(Term term) {
        this.term = term;
    }

    @Override
    public Term getTerm() {
        return term;
    }

    @Override
    public List<Class> getAllClasses() {
        return allClasses;
    }

    public Document getCurrentWebSiteDocument() {
        return currentWebSiteDocument;
    }

    @Override
    public void setCurrentWebSiteDocument(Document currentWebSiteDocument) {
        this.currentWebSiteDocument = currentWebSiteDocument;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    @Override
    public void startScraper() {
        setWebSiteFromTerm();
        retrieveWebPage();
        log.info("Retrieved the following website: " + websiteURL);
        try {
            if(isValidWebSiteWithClasses()) {
                log.info("Starting scraper for " + getNumberOfClasses() + " classes.");
                scrapeAndRetrieveAllClasses();
                log.info("Scraping finished. Retrieved " + allClasses.size() + " classes.");
                log.info("All classes are now the ClassScraper object. The variable, allClasses, holds all of the classes.");
                log.info("Access to the variable, allClasses, can be done by invoking getAllClasses() on the ClassScraper object.");
            }
            else {
                log.info("Invalid website. Stopping scraper.");
            }
        }
        catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void startScraperForMultipleTerms() {
        for (Term t : terms) {
            term = t;
            startScraper();
        }
    }

    @Override
    public void setWebSiteFromTerm() {
        if(term.getTermID().length() > 0)
            websiteURL = URLBuilder.createURLForTermOnly(term);
        else
            log.info("Invalid term. Please validate that the term is set.");
    }

    @Override
    public boolean isValidWebSiteWithClasses() {
        try {
            Elements allClasses = currentWebSiteDocument.select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml());
            return allClasses.size() != 0;
        }
        catch (NullPointerException e) {
            log.info("Invalid website. Please validate that the term is set.");
            return false;
        }
    }

    @Override
    public void retrieveWebPage() {
        try {
            log.info("Retrieving web page...");
            Connection.Response response = Jsoup.connect(websiteURL)
                .ignoreContentType(true)
                .userAgent(USER_AGENT_STRING)
                .referrer(REFERRAL_URL)
                .timeout(12000)
                .followRedirects(true)
                .execute();
            setCurrentWebSiteDocument(response.parse());
            log.info("Retrieved web page! URL is: " + getWebsiteURL());
        }
        catch (IOException e) {
            log.error("Unable to retrieve web page with error: " + e);
        }
    }

    @Override
    public void scrapeCurrentPageAndReturnAsListOfClass() {
        log.info("Scraping current page.");
        allClassesForAGivenDocument = currentWebSiteDocument
                .select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml())
                .stream()
                .map(ScrapeHTMLElements::getClassFromAClassElement)
                .collect(toList());
    }

    @Override
    public void advanceToNextPage() {
        websiteURL = URLBuilder.incrementPageNumber(websiteURL);
    }

    @Override
    public int getNumberOfClasses() {
        log.info("Getting number of classes.");
        return ScrapeHTMLElements.getNumberOfClasses(currentWebSiteDocument);
    }

    @Override
    public void scrapeAndRetrieveAllClasses() {
        do {
            scrapeCurrentPageAndReturnAsListOfClass();
            allClasses.addAll(allClassesForAGivenDocument);
            advanceToNextPage();
            retrieveWebPage();
        } while (isValidWebSiteWithClasses() && pageLimit-- > 0);
    }
}
