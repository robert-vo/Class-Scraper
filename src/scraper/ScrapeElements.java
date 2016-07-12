package scraper;

import org.jsoup.nodes.Element;

/**
 * Created by Robert on 7/11/16.
 */
public class ScrapeElements {

    public static String getNameAndCourseNumber(Element e) {
        return e.select(HTMLElements.NAME_AND_CRN_NUMBER.getHtml()).text();
    }

    public static String getCourseTitle(Element e) {
        return e.select(HTMLElements.COURSE_TITLE.getHtml()).text();
    }

    public static String getCourseStatus(Element e) {
        return e.select(HTMLElements.CLASS_STATUS.getHtml()).text();
    }

    public static String getClassAttributes(Element e) {
        String attributes = e.select(HTMLElements.CLASS_ATTRIBUTES.getHtml()).text();
        return attributes.isEmpty() ? "No attributes" : attributes;
    }

}
