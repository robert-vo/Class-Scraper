package com.scraper.main;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

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
    public static String createURLForTermAndSubject(Term term, Subject subject) {
        final String generatedURL = createURLForTermOnly(term) + generateParameter(URLParameters.subject, subject.name());
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

        String newURL;

        if(!url.contains("page")) {
            newURL = url + generateParameter(URLParameters.page, String.valueOf(requestPageNumber));
        }
        else {
            newURL =  url.substring(0, url.indexOf("page"))  + "page=" + requestPageNumber;
        }

        log.info("The URL after the changing of page number is: " + newURL);
        return newURL;
    }

    /**
     * Extracts the term parameter value from a given URL and returns the result as an integer.
     *
     * @param url The URL that contains a term parameter.
     * @return The value of the term parameter.
     */
    public static int extractTermParameter(String url) {
        try {
            return Integer.parseInt(extractStringTermParameter(url));
        }
        catch (Exception e) {
            log.error("Unable to extract term parameter from " + url + " with error " + e + ".");
            return 0;
        }
    }

    /**
     * Extracts the term parameter value from a given URL.
     *
     * @param url The URL that contains a term parameter.
     * @return The value of the term parameter.
     */
    public static String extractStringTermParameter(String url) {
        String[] parameterValuePairs = url.split("\\?")[1].split("&");
        Map<String, String> parameterValueMap = new HashMap<>();

        for (String parameterValuePair : parameterValuePairs) {
            String key = parameterValuePair.split("=")[0];
            String value = parameterValuePair.split("=")[1];

            parameterValueMap.put(key, value);
        }

        return parameterValueMap.get("term");
    }

    /**
     * Modifies the term parameter to include the session value.
     * This is done by appending a hyphen and a value corresponding the session, such as,
     * 1990-2, for the Term 1990 and Session 2 and also, 2000-MIN, for the Term 2000 and Session MIN.
     *
     * @param URL The URL that contains a term parameter to be modified.
     * @param session The session to be appended to the term parameter value.
     * @return The URL with the term modified to include the session value.
     */
    public static String modifyTermParameterValueForSession(String URL, Session session) {
        if(URL.contains("term")) {
            String newTerm = extractStringTermParameter(URL) + "-" + session.sessionName;

            log.info("Modifying the URL: " + URL + " to include session: " + session.sessionName);

            String newURL = URL.replaceAll("term=[^&]+","term=" + newTerm);

            log.info("The new URL is: " + newURL);
            return newURL;
        }
        else {
            log.error("The URL does not have a term parameter.");
            return "";
        }
    }

    /**
     * Adds a subject parameter and value to the URL.
     *
     * @param URL The URL that will have the subject parameter appended to it.
     * @param subject The subject to be appended to the URL.
     * @return The URL with a subject parameter and value appended to it.
     */
    public static String addSubjectParameterToURL(String URL, Subject subject) {
        log.info("Adding subject, " + subject + ", to URL: " + URL);
        final String newURL = URL + generateParameter(URLParameters.subject, subject.name());
        log.info("The URL after the subject addition is: " + newURL);
        return newURL;
    }
}
