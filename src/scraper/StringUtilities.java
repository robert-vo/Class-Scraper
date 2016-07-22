package scraper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StringUtilities {
    static String REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES = "[\\(\\)]";
    static String REGEX_TO_GET_EMAIL_FROM_HREF_TAG       = "[\\t\\n\\r]";
    static String REGEX_FOR_BOTH_HYPHENS                 = "[-–]";
    static String REGEX_FOR_SPACES                       = "[ ]";

    static String extractTextBetweenParentheses(Elements e) {
        return e.stream()
                .findFirst()
                .map(Element::text)
                .map(str -> str.split(REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES))
                .orElse(new String[]{"0", "0"})[1];
    }

    static String extractEmailFromHREFTag(Elements e) {
        return e.stream()
                .map(e1 -> e1.attr("href"))
                .map(e1 -> e1.replaceAll(REGEX_TO_GET_EMAIL_FROM_HREF_TAG, ""))
                .map(e1 -> e1.substring(7))
                .findFirst()
                .orElse("Unknown Email");
    }

    static String extractTextBetweenParentheses(String str) {
        try {
            return str.split(REGEX_TO_GET_CHARS_BETWEEN_PARENTHESES)[1];
        }
        catch (Exception e) {
            return "";
        }
    }

    static String extractTextBeforeParentheses(String str) {
        return str.substring(0, str.indexOf('(') - 1);
    }

    private static String splitByCharacterAndExtractHalf(String wholeString, boolean half, String splitQualifier) {
        if(isNullOrEmpty(wholeString)) {
            return "";
        }

        String[] splitString = wholeString.split(splitQualifier);
        if(splitString.length != 2) {
            return half ? splitString[0].trim() : "";
        }
        else {
            return half ? splitString[0].trim() : splitString[1].trim();
        }
    }

    static String splitByHyphenAndExtractHalf(String wholeString, boolean half) {
        return splitByCharacterAndExtractHalf(wholeString, half, REGEX_FOR_BOTH_HYPHENS);
    }

    static String splitBySpaceAndExtractHalf(String wholeString, boolean half) {
        return splitByCharacterAndExtractHalf(wholeString, half, REGEX_FOR_SPACES);
    }

    static boolean isNullOrEmpty(String str) {
        return str == null || str.equals("");
    }
}
