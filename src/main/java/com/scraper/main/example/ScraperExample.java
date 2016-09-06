package com.scraper.main.example;

import com.scraper.main.*;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class, ScraperExample, showcases how to use the scraper for a term or multiple terms.
 * The program is a CLI and functions independently of ScraperGUI.
 *
 * @author Robert Vo
 */
public class ScraperExample {

    private final static String JDBC_DRIVER         = "com.mysql.jdbc.Driver";
    private final static String DATABASE_URL        = "jdbc:mysql://localhost/class";
    private final static String USER_NAME           = "root";
    private final static String PASS_WORD           = "password";
    private final static int    PAGE_LIMIT          = 1;
    private final static int    INITIAL_PAGE_LIMIT  = 5;
    private static       Logger log                 = Logger.getLogger(ScraperExample.class);

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        runScraperForFall2016Session1MathClasses();
        log.info(retrieveTimeTakenFromStart(startTime));

        startTime = System.currentTimeMillis();
        runScraperForSpring2017();
        log.info(retrieveTimeTakenFromStart(startTime));

        startTime = System.currentTimeMillis();
        runScraperForMultipleTerms();
        log.info(retrieveTimeTakenFromStart(startTime));
    }

    /**
     * Runs the scraper for Fall 2016, Session 1, Math classes, starting on page 5 and ending on page 7.
     */
    private static void runScraperForFall2016Session1MathClasses() {
        ClassScraper classScraper;

        /**
         * Initializing the class scraper using the year (int) and semester (String).
         */
        classScraper = new ClassScraper(2016, "Fall");

        /**
         * Restricts the scraper to only regular academic session classes.
         */
        classScraper.setSessionOnScraper(Session.REGULAR_ACADEMIC_SESSION);

        /**
         * Restricts the scraper to only Math classes.
         */
        classScraper.setSubjectOnScraper(Subject.MATH);

        /**
         * Sets the initial page that the scraper will start on.
         */
        classScraper.setInitialPageNumber(INITIAL_PAGE_LIMIT);

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
     * Runs the scraper for Summer 2016, Fall 2016 and Spring 2017.
     */
    private static void runScraperForMultipleTerms() {
        ClassScraper classScraper;

        /**
         * List of terms that the scraper will be crawling through.
         */
        List<Term> terms = new LinkedList<>(
                Arrays.asList(Term.SUMMER_2016, Term.FALL_2016, Term.SPRING_2017));

        /**
         * Initializing the scraper with the list of terms.
         */
        classScraper = new ClassScraper(terms);

        /**
         * This sets the limit on how many pages are scraped per term.
         * In this case, since there are 3 terms being scraped with a page limit of 2,
         * 3x2 = 6 pages will be scraped.
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
        try (DatabaseOperations databaseOperations = new DatabaseOperations(JDBC_DRIVER,
                DATABASE_URL, USER_NAME, PASS_WORD)) {
            databaseOperations.performUpdateOrInsertForAllClass(classScraper.getAllClasses());
        } catch (Exception e) {
            log.error("Error occurred during database operations.");
        }
    }

    /**
     * Returns a String that displays how many milliseconds it took to complete the scraper.
     *
     * @param startTime The time the scraper started on.
     * @return A message that displays in the console about the length of the scraper.
     */
    private static String retrieveTimeTakenFromStart(long startTime) {
        long endTime = System.currentTimeMillis() - startTime;
        long hours = TimeUnit.HOURS.convert(endTime, TimeUnit.MILLISECONDS);
        long minutes = TimeUnit.MINUTES.convert(endTime, TimeUnit.MILLISECONDS);
        long seconds = TimeUnit.SECONDS.convert(endTime, TimeUnit.MILLISECONDS);

        return "The time taken to perform the scraping and database operations was: " +
                hours + " hours, " + minutes + " minutes and " + seconds + " seconds.";
    }

}
