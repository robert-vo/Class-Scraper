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
    private ScraperConstraints  scraperConstraints = new ScraperConstraints();
    private static Logger       log = Logger.getLogger(ClassScraper.class);

    /**
     * Constructs the ClassScraper using the Term enum.
     *
     * @param term The Term enum that represents a year and semester.
     */
    public ClassScraper(Term term) {
        this.term = term;
        log.info("Initialized ClassScraper with term " + term.toString());
    }

    /**
     * Constructs the ClassScraper using the year and semester of the classes.
     *
     * @param year The year, starting with 2015, where the scraped classes are offered.
     * @param semester The semester, which can be "Spring", "Summer", or "Fall",
     *                 where the scraped classes are offered.
     */
    public ClassScraper(int year, String semester) {
        String termValue = semester.toUpperCase() + "_" + year;
        this.term = Term.valueOf(termValue);
        log.info("Initialized ClassScraper with term " + term.toString());
    }

    /**
     * Constructs the ClassScraper for multiple Terms.
     *
     * @param terms The List of Term that represents multiple years and semesters.
     */
    public ClassScraper(List<Term> terms) {
        this.terms = terms;
        log.info("Initialized ClassScraper with terms " + terms.toString());
    }

    /**
     * Sets the class session to be scraped. If left untouched, the scraper will
     * scrape all classes, regardless of session.
     *
     * @param session The session (1, 2, 3, 4, 5, 6, MIN) where the classes fall under.
     */
    public void setSessionOnScraper(Session session) {
        log.info("Setting session constraint, " + session + " on the scraper.");
        scraperConstraints.sessionConstraint = session;
    }

    /**
     * Applies the session constraint on the scraper.
     */
    private void applySessionConstraintOnWebsiteURL() {
        log.info("Replacing current term to include session: " +
                scraperConstraints.sessionConstraint);
        websiteURL = URLBuilder.modifyTermParameterValueForSession(websiteURL, scraperConstraints.sessionConstraint);
    }

    /**
     * Sets the class subject to be scraped. If left untouched, the scraper will
     * scrape all classes, regardless of subject.
     *
     * @param subject The subject (AAS, COSC, etc.) where the classes fall under.
     */
    public void setSubjectOnScraper(Subject subject) {
        log.info("Setting subject constraint, " + subject + " on the scraper.");
        scraperConstraints.subjectConstraint = subject;
    }

    /**
     * Applies the subject constraint on the scraper.
     */
    private void applySubjectConstraintOnWebsiteURL() {
        log.info("Applying subject parameter with value, " +
                scraperConstraints.subjectConstraint.fullSubjectName + " to website URL.");
        websiteURL = URLBuilder.addSubjectParameterToURL(websiteURL, scraperConstraints.subjectConstraint);
    }

    /**
     * Sets the initial page number of the scraper. If left untouched, the scraper will
     * start at page 1.
     *
     * @param initialPageNumber The initial page number of the scraper.
     */
    public void setInitialPageNumber(int initialPageNumber) {
        log.info("Setting initial page number constraint, " + initialPageNumber + " on the scraper.");
        scraperConstraints.initialPageNumberConstraint = initialPageNumber;
    }

    /**
     * Applies the initial page number constraint on the scraper.
     */
    private void applyInitialPageNumberConstraintOnWebsiteURL() {
        log.info("The scraper will now start on page " + scraperConstraints.initialPageNumberConstraint + ".");
        websiteURL = URLBuilder.changePageNumber(websiteURL, scraperConstraints.initialPageNumberConstraint);
    }

    /**
     * Sets a limit on how many pages the ClassScraper can crawl through.
     *
     * @param pageLimit The number of pages to be scraped.
     */
    public void setPageLimit(int pageLimit) {
        if(pageLimit > 0) {
            log.info("The scraper will only run for " + pageLimit + " pages.");
            scraperConstraints.pageLimitConstraint = pageLimit;
        }
        else {
            log.warn("Attempted to set a page limit, " + pageLimit + " that is not positive. " +
                    "Continuing with the scraper with no page limit.");
        }
    }

    /**
     * Mutator (setter) method for Term.
     *
     * @param term The term value to be associated with the ClassScraper object.
     */
    public void setTerm(Term term) {
        this.term = term;
    }

    /**
     * Accessor (getter) method for Term.
     *
     * @return The term value associated with the ClassScraper object.
     */
    public Term getTerm() {
        return term;
    }

    /**
     * Accessor (getter) method for List of Class.
     *
     * @return List of Class associated with the ClassScraper object.
     */
    public List<Class> getAllClasses() {
        return allClasses;
    }

    /**
     * Accessor (getter) method for Document, an object which represents the current web page in a JSoup format.
     *
     * @return The Document that represents the current web page.
     */
    public Document getCurrentWebSiteDocument() {
        return currentWebSiteDocument;
    }

    /**
     * Mutator (setter) method for Document, an object which represents the current web page in a JSoup format.
     *
     * @param currentWebSiteDocument The term value to be associated with the ClassScraper object.
     */
    public void setCurrentWebSiteDocument(Document currentWebSiteDocument) {
        this.currentWebSiteDocument = currentWebSiteDocument;
    }

    /**
     * Accessor (getter) method for the String representation of the website URL.
     *
     * @return The website URL that represents the current page the ClassScraper is on.
     */
    public String getWebsiteURL() {
        return websiteURL;
    }

    /**
     * Starts the class scraper. At the conclusion of the class scraping,
     * the scraped classes can be accessed using the getAllClasses method.
     */
    @Override
    public void startScraper() {
        if(term == null && terms == null) {
            log.error("The term to be scraped has not been set. Aborting scraper.");
        }
        else {
            setWebSiteURLWithConstraints();
            retrieveAndSetWebPage();
            log.info("Retrieved the following website: " + websiteURL);
            try {
                if (isValidWebSiteWithClasses()) {
                    log.info("Starting scraper for " + getNumberOfClasses() + " classes.");
                    scrapeEachWebPageAndAddToListOfClass();
                    log.info("Scraping finished. Retrieved " + allClasses.size() + " classes.");
                    log.info("All classes are now the ClassScraper object. The variable, allClasses, holds all of the classes.");
                    log.info("Access to the variable, allClasses, can be done by invoking getAllClasses() on the ClassScraper object.");
                } else {
                    log.info("Invalid website. Stopping scraper.");
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }

    private void setWebSiteURLWithConstraints() {
        setWebSiteURLFromTerm();

        if(scraperConstraints.sessionConstraint != null) {
            applySessionConstraintOnWebsiteURL();
        }

        if(scraperConstraints.subjectConstraint != null) {
            applySubjectConstraintOnWebsiteURL();
        }

        if(scraperConstraints.initialPageNumberConstraint > 0) {
            applyInitialPageNumberConstraintOnWebsiteURL();
        }

        if(scraperConstraints.pageLimitConstraint > 0) {
            setPageLimit(scraperConstraints.pageLimitConstraint);
        }

    }

    /**
     * Iterates through the List of Term and performs the class scraper for each term.
     */
    @Override
    public void startScraperForMultipleTerms() {
        terms.stream().forEach(aTerm -> {
            term = aTerm;
            startScraper();
        });
    }

    /**
     * Generates and sets the website URL for given a Term.
     */
    @Override
    public void setWebSiteURLFromTerm() {
        if(term.getTermID().length() > 0) {
            websiteURL = URLBuilder.createURLForTermOnly(term);
        }
        else {
            log.error("Invalid term. Please validate that the term is set.");
        }
    }

    /**
     * Checks if the web page contains valid classes to be scraped.
     *
     * @return Whether the web page is valid for scraping.
     */
    @Override
    public boolean isValidWebSiteWithClasses() {
        try {
            Elements allClasses = currentWebSiteDocument.select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml());
            return allClasses.size() != 0;
        }
        catch (NullPointerException e) {
            log.error("Invalid website. Please validate that the term is set.");
            return false;
        }
    }

    /**
     * Retrieves the web page from the internet and sets the Documentat web page to be scraped.
     */
    @Override
    public void retrieveAndSetWebPage() {
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

    /**
     * Scrapes the current web page Document and adds the scraped classes
     * to the List of Class.
     */
    @Override
    public void scrapeCurrentPageAndAddToListOfClass() {
        log.info("Scraping current page.");
        allClassesForAGivenDocument = currentWebSiteDocument
                .select(HTMLElements.RETRIEVE_ALL_CLASSES.getHtml())
                .stream()
                .map(ScrapeHTMLElements::getClassFromAClassElement)
                .collect(toList());
    }

    /**
     * Advances the website to the next page. This is done by iterating the page parameter by 1.
     */
    @Override
    public void advanceToNextPage() {
        websiteURL = URLBuilder.incrementPageNumber(websiteURL);
    }

    /**
     * Retrieves the number of classes to be scraped.
     *
     * @return The number of classes to be scraped.
     */
    @Override
    public int getNumberOfClasses() {
        log.info("Getting number of classes.");
        return ScrapeHTMLElements.getNumberOfClasses(currentWebSiteDocument);
    }

    /**
     * Scrapes every single web page, in increasing order, and adds the result,
     * which is a List of Class for that web page, to the entire List of Class.
     */
    @Override
    public void scrapeEachWebPageAndAddToListOfClass() {
        while(canContinueWithNextPage()) {
            scrapeCurrentPageAndAddToListOfClass();
            allClasses.addAll(allClassesForAGivenDocument);
            advanceToNextPage();
            retrieveAndSetWebPage();
        }
    }

    /**
     * Helper method that will determine whether the scraper can continue, or not.
     *
     * @return Whether the scraper can continue on, or not.
     */
    private boolean canContinueWithNextPage() {
        return isValidWebSiteWithClasses() && scraperConstraints.pageLimitConstraint-- > 0;
    }
}
