package com.scraper.main.util;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * StringUtil contains method that assists with extracting
 * specific text from JSoup Elements and Strings.
 *
 * @author Robert Vo
 */
public class StringUtil {
    final static String REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES = "[\\(\\)]";
    final static String REGEX_TO_GET_EMAIL_FROM_HREF_TAG       = "[\\t\\n\\r]";
    final static String REGEX_FOR_BOTH_HYPHENS                 = "[-–]";
    final static String REGEX_FOR_SPACES                       = "[ ]";
    private static Logger log = Logger.getLogger(StringUtil.class);

    /**
     * Extracts the DOM Elements for the first text tag and returns the value between the parentheses.
     *
     * @param e The DOM element that contains specific information (seat number, for example) about an entire class.
     * @return Returns the text between the parentheses.
     */
    public static String extractTextBetweenParentheses(Elements e) {
        return e.stream()
                .findFirst()
                .map(Element::text)
                .map(str -> str.split(REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES))
                .map(str -> str[1])
                .orElse("0");
    }

    /**
     * Extracts the DOM Elements for the anchor tag that has an href element,
     * cleans the text from the anchor tag and returns the result.
     * If an email does not exist, the method will return "Unknown Email".
     *
     * @param e The DOM element that contains the email address of the instructor for a class.
     * @return The email address, if available, of the instructor for a class.
     */
    public static String extractEmailFromHREFTag(Elements e) {
        return e.stream()
                .map(e1 -> e1.attr("href"))
                .map(e1 -> e1.replaceAll(REGEX_TO_GET_EMAIL_FROM_HREF_TAG, ""))
                .map(e1 -> e1.substring(7))
                .findFirst()
                .orElse("Unknown Email");
    }

    /**
     * Extracts and returns the text between the parentheses, if applicable.
     * If the string is invalid, the method will return an empty string.
     *
     * @param str The string that contains a pair of parentheses with a value in between them.
     * @return The text between the parentheses.
     */
    public static String extractTextBetweenParentheses(String str) {
        try {
            return str.split(REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES)[1];
        }
        catch (Exception e) {
            log.error("Unable to extract text between the parentheses for string: " + str);
            return "";
        }
    }

    /**
     * Returns the text before, not including, the parentheses.
     *
     * @param str A String that contains a parentheses.
     * @return The text before, not including, the parentheses.
     */
    public static String extractTextBeforeParentheses(String str) {
        return str.substring(0, str.indexOf('(') - 1);
    }

    /**
     * Splits the parameter, {@param wholeString}, into two with respect to the qualifier, {@param splitQualifier}
     * and returns the first half, given {@param isFirstHalf} is true,
     * or the second half, given {@param isFirstHalf} is false.
     *
     * @param wholeString The string to be split.
     * @param isFirstHalf A condition that determines whether the first, true, or the second, false, half is returned.
     * @param splitQualifier The qualifier used to split the {@param wholeString}.
     * @return The desired String portion of the {@param wholeString}.
     */
    private static String splitByCharacterAndExtractHalf(String wholeString, boolean isFirstHalf, String splitQualifier) {
        if(isNullOrEmpty(wholeString)) {
            return "";
        }

        String[] splitString = wholeString.split(splitQualifier);
        if(splitString.length != 2) {
            return isFirstHalf ? splitString[0].trim() : "";
        }
        else {
            return isFirstHalf ? splitString[0].trim() : splitString[1].trim();
        }
    }

    /**
     * Splits the string into two by the hyphen, "-", and returns the desired half.
     *
     * @param wholeString The string to be split.
     * @param isFirstHalf A condition that determines whether the first, true, or the second, false, half is returned.
     * @return The desired String portion of the {@param wholeString}.
     */
    public static String splitByHyphenAndExtractHalf(String wholeString, boolean isFirstHalf) {
        return splitByCharacterAndExtractHalf(wholeString, isFirstHalf, REGEX_FOR_BOTH_HYPHENS);
    }

    /**
     * Splits the string into two by the hyphen, " ", and returns the desired half.
     *
     * @param wholeString The string to be split.
     * @param isFirstHalf A condition that determines whether the first, true, or the second, false, half is returned.
     * @return The desired String portion of the {@param wholeString}.
     */
    public static String splitBySpaceAndExtractHalf(String wholeString, boolean isFirstHalf) {
        return splitByCharacterAndExtractHalf(wholeString, isFirstHalf, REGEX_FOR_SPACES);
    }

    /**
     * Determines whether a String is null or empty.
     *
     * @param str A String to be checked.
     * @return A boolean, true, if the String is null or empty, or false, if the String is not null or not empty.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.equals("");
    }

}
