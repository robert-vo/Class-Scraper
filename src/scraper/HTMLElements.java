package scraper;

/**
 * Created by Robert on 7/11/16.
 */
public enum HTMLElements {
    RETRIEVE_ALL_CLASSES("ul[id=accordion] > li"),
    NUMBER_OF_CLASSES   ("div > section > article > section > h1");

    final private String html;

    HTMLElements(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }
}
