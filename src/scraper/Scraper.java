package scraper;

import org.jsoup.select.Elements;

public interface Scraper {
    String REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES = "[\\(\\)]";
    String REGEX_TO_GET_EMAIL_FROM_HREF_TAG       = "[\\t\\n]";

    static String extractTextBetweenParentheses(Elements e) {
        try {
            return e.first().text().split(REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES)[1];
        }
        catch (Exception e1) {
            return "0";
        }
    }

    static String extractEmailFromHREFTag(Elements e) {
        try {
            return e.attr("href")
                    .replaceAll(REGEX_TO_GET_EMAIL_FROM_HREF_TAG, "")
                    .substring(7);
        }
        catch (Exception e1) {
            return "Unknown Email";
        }
    }

}
