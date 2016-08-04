package com.scraper.main.example;

import com.scraper.main.ClassScraper;
import com.scraper.main.Term;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class, ScraperExample, showcases how to use the scraper for a term or multiple terms.
 * The program is a CLI and functions independently of ScraperGUI.
 */
public class ScraperExample {

    public static void main(String[] args) {
        runScraperForFall2016();
        runScraperForMultipleTerms();
    }

    private static void runScraperForFall2016() {
        ClassScraper classScraper;

        /**
         * Initializing the class scraper using the Term enum.
         */
        classScraper = new ClassScraper(Term.FALL_2016);

        /**
         * Initializing the class scraper using the explicit term value.
         * In this case, "2000" is equal to Term.FALL_2016.
         * "2010" is equal to Term.SPRING_2017, "2020" is equal to Term.SUMMER_2017 and so on.
         */
        classScraper = new ClassScraper(Term.returnTermFromString("2000"));

        /**
         * Initializing the class scraper using the year (int) and semester (String).
         */
        classScraper = new ClassScraper(2016, "Fall");

        /**
         * This sets the limit on how many pages are scraped per term.
         */
        classScraper.setPageLimit(1);

        /**
         * Starting the scraper.
         */
        classScraper.startScraper();

        /**
         * Retrieving information from the scraper.
         */
        classScraper.getAllClasses().stream().forEach(System.out::println);
    }

    private static void runScraperForMultipleTerms() {
        ClassScraper classScraper;

        /**
         * List of terms that the scraper will be crawling through.
         */
        List<Term> terms = new LinkedList<>(
                Arrays.asList(Term.FALL_2015, Term.SPRING_2016, Term.SUMMER_2016, Term.FALL_2016));

        /**
         * Initializing the scraper with the list of terms.
         */
        classScraper = new ClassScraper(terms);

        /**
         * This sets the limit on how many pages are scraped per term.
         * In this case, since there are 4 terms being scraped with a page limit of 1,
         * 4x1 = 4 pages will be scraped.
         */
        classScraper.setPageLimit(1);

        /**
         * Starting the scraper for all terms.
         */
        classScraper.startScraperForMultipleTerms();

        /**
         * Retrieving information from the scraper.
         */
        classScraper.getAllClasses().stream().forEach(System.out::println);
    }
}
