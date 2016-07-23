package com.scraper.main;

import com.scraper.ui.ScraperGUI;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClassScraper implements Scraper {

    Term                term;
    public String       websiteURL;
    public Document     currentWebSiteDocument;
    List<Class>         allClassesForAGivenDocument;
    List<Class>         allClasses  = new ArrayList<>();
    int                 pageLimit   = Integer.MAX_VALUE;

    public ClassScraper(Term term) {
        this.term = term;
    }

    public ClassScraper(int year, String semester) {
        String termValue = semester.toUpperCase() + "_" + year;
        this.term = Term.valueOf(termValue);
    }

    @Override
    public void setPageLimit(int pageLimit) {
        print("The scraper will only run for " + pageLimit + " pages.");
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

    @Override
    public void startScraper() {
        print("Starting Scraper");
        setWebSiteFromTerm();
        retrieveWebPage();
        print("Retrieved the following website: " + websiteURL);
        try {
            if(isValidWebSiteWithClasses()) {
                print("Starting scraper for " + getNumberOfClasses() + " classes.");
                retrieveAllClasses();
                print("Scraping finished. Retrieved " + allClasses.size() + " classes.");
                print("All classes are now the ClassScraper object. The variable, allClasses, holds all of the classes.");
                print("Access to the variable, allClasses, can be done by invoking getAllClasses() on the ClassScraper object.");
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
        if(term.getTermID().length() > 0)
            websiteURL = URLBuilder.createURLForTermOnly(term);
        else
            print("Invalid term. Please validate that the term is set.");
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
    public void setCurrentWebSiteDocument(Document doc) {
        this.currentWebSiteDocument = doc;
    }

    @Override
    public void retrieveWebPage() {
        try {
            Connection.Response response = Jsoup.connect(websiteURL)
                .ignoreContentType(true)
                .userAgent(USER_AGENT_STRING)
                .referrer(REFERRAL_URL)
                .timeout(12000)
                .followRedirects(true)
                .execute();
            setCurrentWebSiteDocument(response.parse());
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
                .collect(toList());
    }

    @Override
    public void advanceToNextPage() {
        websiteURL = URLBuilder.incrementPageNumber(websiteURL);
    }

    @Override
    public void print(String message) {
        try {
            java.lang.Class.forName("com.scraper.ui.ScraperGUI");
            ScraperGUI.appendToLoggerTextArea(message);
        }
        catch (ClassNotFoundException e) {
            System.out.println(message);
        }
    }

    @Override
    public int getNumberOfClasses() {
        return ScrapeElements.getNumberOfClasses(currentWebSiteDocument);
    }

    @Override
    public void retrieveAllClasses() {
        int counter = 0;
        do {
            scrapeCurrentPageAndReturnAsListOfClass();
            allClasses.addAll(allClassesForAGivenDocument);
            advanceToNextPage();
            retrieveWebPage();
            print("Retrieved the following website: " + websiteURL);
            counter += 1;
        } while (isValidWebSiteWithClasses() && counter < pageLimit);
    }
}
