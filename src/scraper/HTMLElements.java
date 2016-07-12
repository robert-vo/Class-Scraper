package scraper;

/**
 * Created by Robert on 7/11/16.
 */
public enum HTMLElements {
    RETRIEVE_ALL_CLASSES("ul[id=accordion] > li"),
    NUMBER_OF_CLASSES   ("div > section > article > section > h1"),
    NAME_AND_CRN_NUMBER ("h3[class=class-title] > strong"),
    COURSE_TITLE        ("span[class=class-title--course]"),
    CLASS_STATUS        ("span[class^=class--status]"),
    CLASS_ATTRIBUTES    ("small[class=class--attributesList] > span"),
    CLASS_START_DATE    (""),
    CLASS_END_DATE      (""),
    CLASS_DAYS          (""),
    CLASS_TIMES         (""),
    INSTRUCTOR          (""),
    INSTRUCTOR_EMAIL    (""),
    LOCATION            (""),
    ROOM                (""),
    FORMAT              (""),
    DESCRIPTION         (""),
    CLASS_DURATION      (""),
    SESSION             (""),
    CLASS_COMPONENT     (""),
    CLASS_SYLLABUS      (""),
    NOTES               ("");

    final private String html;

    HTMLElements(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }
}
