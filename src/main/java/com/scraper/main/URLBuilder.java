package com.scraper.main;

import org.apache.log4j.Logger;

/**
 * URLBuilder constructs and modifies a given URL that will be used for the scraping.
 *
 * @author Robert Vo
 */

public class URLBuilder {

    final static private String BASE_URL = "http://classbrowser.uh.edu/classes?";
    private static Logger log = Logger.getLogger(URLBuilder.class);

    /**
     * Creates a URL from the base URL.
     *
     * @return A StringBuilder of the base URL.
     */
    public static StringBuilder createNewURL() {
        return new StringBuilder(BASE_URL);
    }

    /**
     * Creates a URL from the base URL with a parameter for the term,
     * where a term represents a specific year and semester.
     *
     * @param term The term that represents a specific year and semester.
     * @return A String representing the URL to be used for the scraper.
     */
    public static String createURLForTermOnly(Term term) {
        final String generatedURL = createNewURL()
                                        .append(generateParameter(URLParameters.term, term.getTermID()))
                                        .toString();

        log.info("The generated URL for term: " + generatedURL);

        return generatedURL;
    }

    /**
     * Creates a URL from the base URL with a parameter for the subject of the classes and term,
     * where a term represents a specific year and semester.
     *
     * @param term The term that represents a specific year and semester.
     * @param subject The subject where all of the classes to be scraped are in.
     * @return A String representing the URL to be used for the scraper.
     */
    public static String createURLForTermAndSubject(Term term, String subject) {
        final String generatedURL = createURLForTermOnly(term) + generateParameter(URLParameters.subject, subject);

        log.info("The generated URL for term and subject: " + generatedURL);

        return generatedURL;
    }

    /**
     * Generates a parameter that will be appended to the end of a URL.
     * A parameter passed through the URL will have the format of "&key=value" or "key=value".
     *
     * @param parameter The key of the URL parameter.
     * @param value The value of the URL parameter.
     * @return A new parameter, in the format of "&key=value" or "key=value".
     */
    public static String generateParameter(URLParameters parameter, String value) {
        StringBuilder newParameter = new StringBuilder();

        if(!parameter.equals(URLParameters.term)) {
            newParameter.append("&");
        }

        newParameter.append(parameter.toString())
                    .append("=")
                    .append(value);

        log.info("The new parameter generated is: " + newParameter.toString());
        return newParameter.toString();
    }

    /**
     * Increments the page number parameter value of a given URL.
     *
     * @param pageURL The URL that contains a page number parameter.
     * @return A URL with the page number incremented.
     */
    public static String incrementPageNumber(String pageURL) {
        String incrementedPageURL;

        if(!pageURL.contains("page")) {
            incrementedPageURL = pageURL + generateParameter(URLParameters.page, "2");

            log.info("Adding page number parameter to the URL. Returning page URL: " + incrementedPageURL);

            return incrementedPageURL;
        }
        else {
            int currentPage = Integer.parseInt(pageURL.substring(pageURL.indexOf("page")).split("=")[1]);
            incrementedPageURL = changePageNumber(pageURL, currentPage + 1);

            log.info("Incremented page from " + currentPage + " to " + (currentPage + 1) + ".");

            return incrementedPageURL;
        }
    }

    /**
     * Modifies the page number to the request page number value.
     *
     * @param url The URL that contains a page number parameter.
     * @param requestPageNumber The request page number.
     * @return A URL with the page number modified with respect to the requested page number.
     */
    public static String changePageNumber(String url, int requestPageNumber) {
        log.info("Changing page number to " + requestPageNumber + ".");

        if(!url.contains("page")) {
            return url + generateParameter(URLParameters.page, String.valueOf(requestPageNumber));
        }
        else {
            return url.substring(0, url.indexOf("page"))  + "page=" + requestPageNumber;
        }
    }

    /**
     * Extracts the term parameter value from a given URL.
     *
     * @param url The URL that contains a term parameter.
     * @return The value of the term parameter.
     */
    public static int extractTermParameter(String url) {
        try {
            return Integer.parseInt(url.split("\\?")[1].split("&")[0].split("=")[1]);
        }
        catch (Exception e) {
            log.error("Unable to extract term parameter from " + url + " with error " + e + ".");
            return 0;
        }
    }
}
