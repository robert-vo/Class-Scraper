package com.scraper.main.example;

import com.scraper.main.ClassScraper;
import com.scraper.main.DatabaseOperations;
import com.scraper.main.Term;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class, ScraperExample, showcases how to use the scraper for a term or multiple terms.
 * The program is a CLI and functions independently of ScraperGUI.
 *
 * @author Robert Vo
 */
public class ScraperExample {

    private final static String JDBC_DRIVER     = "com.mysql.jdbc.Driver";
    private final static String DATABASE_TABLE  = "class";
    private final static String DATABASE_URL    = "jdbc:mysql://localhost";
    private final static String USER_NAME       = "root";
    private final static String PASS_WORD       = "password";
    private final static int    PAGE_LIMIT      = 2;
    private static Logger log = Logger.getLogger(ScraperExample.class);

    public static void main(String[] args) {
        runScraperForFall2016();
        runScraperForSpring2017();
        runScraperForMultipleTerms();
    }

    /**
     * Runs the scraper for Fall 2016.
     */
    private static void runScraperForFall2016() {
        ClassScraper classScraper;

        /**
         * Initializing the class scraper using the year (int) and semester (String).
         */
        classScraper = new ClassScraper(2016, "Fall");

        /**
         * This sets the limit on how many pages are scraped per term.
         */
        classScraper.setPageLimit(PAGE_LIMIT);

        /**
         * Starting the scraper.
         */
        classScraper.startScraper();

        /**
         * Retrieves and prints out each each class scraped from the web pages.
         */
        classScraper.getAllClasses().stream().forEach(e -> log.info(e));

        /**
         * Performs database operations with the scraped classes.
         */
        performDatabaseOperationsWithScrapedClasses(classScraper);
    }

    /**
     * Runs the scraper for Spring 2017.
     */
    private static void runScraperForSpring2017() {
        ClassScraper classScraper;

        /**
         * Initializing the class scraper using the Term enum.
         */
        classScraper = new ClassScraper(Term.SPRING_2017);

        /**
         * This sets the limit on how many pages are scraped per term.
         */
        classScraper.setPageLimit(PAGE_LIMIT);

        /**
         * Starting the scraper.
         */
        classScraper.startScraper();

        /**
         * Retrieves and prints out each each class scraped from the web pages.
         */
        classScraper.getAllClasses().stream().forEach(e -> log.info(e));

        /**
         * Performs database operations with the scraped classes.
         */
        performDatabaseOperationsWithScrapedClasses(classScraper);
    }

    /**
     * Runs the scraper for Fall 2015, Spring 2016, Summer 2016, Fall 2016.
     */
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
        classScraper.setPageLimit(PAGE_LIMIT);

        /**
         * Starting the scraper for all terms.
         */
        classScraper.startScraperForMultipleTerms();

        /**
         * Retrieves and prints out each each class scraped from the web pages.
         */
        classScraper.getAllClasses().stream().forEach(e -> log.info(e));

        /**
         * Performs database operations with the scraped classes.
         */
        performDatabaseOperationsWithScrapedClasses(classScraper);
    }

    /**
     * Performs the database operations (update, insert) with the scraped classes.
     *
     * @param classScraper The completed ClassScraper that holds a List of Class.
     */
    private static void performDatabaseOperationsWithScrapedClasses(ClassScraper classScraper) {
        try {
            DatabaseOperations databaseOperations = new DatabaseOperations(JDBC_DRIVER, DATABASE_TABLE,
                    DATABASE_URL, USER_NAME, PASS_WORD);
            databaseOperations.performDatabaseActions(classScraper.getAllClasses());
        }
        catch (IOException e) {
            log.error("Error occurred during database operations.");
        }
    }
}
