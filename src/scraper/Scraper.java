package scraper;

import org.jsoup.select.Elements;

public interface Scraper {
    String REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES = "[\\(\\)]";

    static String extractTextBetweenParentheses(Elements e) {
        return e.first().text().split(REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES)[1];
    }

}
