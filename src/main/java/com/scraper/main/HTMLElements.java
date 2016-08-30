package com.scraper.main;

/**
 * HTMLElements Enum stores the html tags where a certain attribute lies on the web page.
 * HTMLElements will be used by JSoup to scrape class information.
 *
 * @author Robert Vo
 */
public enum HTMLElements {
    RETRIEVE_ALL_CLASSES        ("ul[id=accordion] > li"),
    NUMBER_OF_CLASSES           ("div > section > article > section > h1"),
    CLASS_NAME_AND_CRN_NUMBER   ("h3[class=class-title] > strong"),
    CLASS_TITLE                 ("span[class=class-title--course]"),
    CLASS_STATUS_AND_SEATS      ("span[class^=class--status]"),
    CLASS_ATTRIBUTES            ("small[class=class--attributesList] > span"),
    CLASS_DATES                 ("li[class=float-list--row] :eq(0) > span[class$=body]:not(:has(a))"),
    CLASS_DAYS_TIMES            ("li[class=float-list--row] > ul > li:contains(Days) > span[class=class-info--body]"),
    CLASS_INSTRUCTOR            ("li[class=float-list--row] :eq(0) > span[class$=body] > a"),
    CLASS_INSTRUCTOR_EMAIL      ("li[class=float-list--row] :eq(0) > span[class$=body] > a"),
    CLASS_CAMPUS_LOCATION       ("li[class=float-list--row] > ul > li:has(span[class=class-info--label]:contains(Location)) > span[class=class-info-body]"),
    CLASS_ROOM                  ("li[class=float-list--row] > ul > li:has(span[class=class-info--label]:contains(Room)) > span[class=class-info-body]"),
    CLASS_FORMAT                ("li[class=float-list--row] > ul > li:has(span[class=class-info--label]:contains(Format)) > span[class=class-info--body]"),
    CLASS_DESCRIPTION           ("div[class=panel-collapse  collapse  in  class--body] > ul[class=list-unstyled] > li > p:contains(Description)"),
    CLASS_DURATION              ("div[class=panel-collapse  collapse  in  class--body] > ul[class=list-unstyled] > li > ul > li:contains(Duration)"),
    CLASS_SESSION               ("div[class=panel-collapse  collapse  in  class--body] > ul > li > ul > li:contains(Session)"),
    CLASS_COMPONENT             ("div[class=panel-collapse  collapse  in  class--body] > ul[class=list-unstyled] > li > ul > li:contains(Component)"),
    CLASS_SYLLABUS              ("div[class=panel-collapse  collapse  in  class--body] > ul[class=list-unstyled] > li > ul > li:contains(Syllabus) > a");

    final private String html;

    HTMLElements(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }
}
