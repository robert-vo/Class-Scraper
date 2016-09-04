package com.scraper;

import com.scraper.main.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class URLBuilderTest {

    final String URL_WITH_TERM_1990                 = "http://classbrowser.uh.edu/classes?term=1990";
    final String URL_WITH_TERM_1990_SESSION_2       = "http://classbrowser.uh.edu/classes?term=1990-2";
    final String URL_WITH_TERM_2010                 = "http://classbrowser.uh.edu/classes?term=2010";
    final String URL_WITH_TERM_1990_SUBJECT_COSC    = "http://classbrowser.uh.edu/classes?term=1990&subject=COSC";
    final String URL_WITH_TERM_1990_SUBJECT_COSC_SESSION_2    = "http://classbrowser.uh.edu/classes?term=1990-2&subject=COSC";
    final String URL_WITH_TERM_1990_PAGE_2          = "http://classbrowser.uh.edu/classes?term=1990&page=2";
    final String URL_WITH_TERM_2010_SUBJECT_COSC    = "http://classbrowser.uh.edu/classes?term=2010&subject=COSC";
    final String URL_WITH_TERM_2010_SUBJECT_COSC_MIN_SESSION   = "http://classbrowser.uh.edu/classes?term=2010-MIN&subject=COSC&page=2";
    final String URL_WITH_TERM_2010_SUBJECT_COSC_PAGE_2 = "http://classbrowser.uh.edu/classes?term=2010&subject=COSC&page=2";
    final String URL_WITH_TERM_2000_PAGE_2          = "http://classbrowser.uh.edu/classes?term=2000&page=2";
    final String URL_WITH_TERM_2000_PAGE_3          = "http://classbrowser.uh.edu/classes?term=2000&page=3";

    @Test
    public void testForTermEqualToSummer2016() {
        assertEquals(URLBuilder.createURLForTermOnly(Term.SUMMER_2016), URL_WITH_TERM_1990);
    }

    @Test
    public void testForTermEqualToSpring2017() {
        assertEquals(URLBuilder.createURLForTermOnly(Term.SPRING_2017), URL_WITH_TERM_2010);
    }

    @Test
    public void testForTermEqualToSummer2016AndSubjectEqualToCOSC() {
        assertEquals(URLBuilder.createURLForTermAndSubject(Term.SUMMER_2016, Subject.COSC), URL_WITH_TERM_1990_SUBJECT_COSC);
    }

    @Test
    public void testForTermEqualToSpring2017AndSubjectEqualToCOSC() {
        assertEquals(URLBuilder.createURLForTermAndSubject(Term.SPRING_2017, Subject.COSC), URL_WITH_TERM_2010_SUBJECT_COSC);
    }

    @Test
    public void testForAddingPageNumberToExistingURL() {
        String initialPage = URLBuilder.createURLForTermOnly(Term.FALL_2016);
        String secondPage = initialPage + URLBuilder.generateParameter(URLParameters.page, "2");
        assertEquals(secondPage, URL_WITH_TERM_2000_PAGE_2);
    }

    @Test
    public void testIncrementPageNumber() throws Exception {
        String secondPage = URL_WITH_TERM_2000_PAGE_2;
        String thirdPage = URLBuilder.incrementPageNumber(secondPage);
        assertEquals(thirdPage, URL_WITH_TERM_2000_PAGE_3);
    }

    @Test
    public void testIncrementPageNumberAppendsPageTwo() throws Exception {
        String newURL = URLBuilder.incrementPageNumber(URL_WITH_TERM_1990);
        assertEquals(newURL, URL_WITH_TERM_1990_PAGE_2);
    }

    @Test
    public void testExtractTermParameterNoPage() {
        assertEquals(URLBuilder.extractTermParameter(URL_WITH_TERM_1990), 1990);
    }

    @Test
    public void testExtractTermParameterOnPage2() {
        assertEquals(URLBuilder.extractTermParameter(URL_WITH_TERM_2000_PAGE_2), 2000);
    }

    @Test
    public void testExtractTermParameterWithSubjectCOSC() {
        assertEquals(URLBuilder.extractTermParameter(URL_WITH_TERM_1990_SUBJECT_COSC), 1990);
    }

    @Test
    public void testIncrementPageURLFrom119To120() {
        assertEquals(URLBuilder.incrementPageNumber("http://classbrowser.uh.edu/classes?term=1970&page=119"),
                "http://classbrowser.uh.edu/classes?term=1970&page=120");
    }

    @Test
    public void testChangePageNumberTo1000() {
        assertEquals(URLBuilder.changePageNumber(URL_WITH_TERM_1990_SUBJECT_COSC, 1000),
                URL_WITH_TERM_1990_SUBJECT_COSC + "&page=1000");
    }

    @Test
    public void testChangePageNumberFrom1To1000() {
        assertEquals(URLBuilder.changePageNumber("http://classbrowser.uh.edu/classes?term=1970&page=1", 1000),
                "http://classbrowser.uh.edu/classes?term=1970&page=1000");
    }

    @Test
    public void testChangePageNumberFrom1000To1() {
        assertEquals(URLBuilder.changePageNumber("http://classbrowser.uh.edu/classes?term=1970&page=1000", 1),
                "http://classbrowser.uh.edu/classes?term=1970&page=1");
    }

    @Test
    public void testChangePageNumberFromNoPageTo123123() {
        assertEquals(URLBuilder.changePageNumber(URL_WITH_TERM_2010_SUBJECT_COSC, 123123),
                URL_WITH_TERM_2010_SUBJECT_COSC + "&page=123123");
    }

    @Test
    public void testGenerateURLForSession2Term1990() {
        String URL = URLBuilder.createURLForTermOnly(Term.SUMMER_2016);
        URL = URLBuilder.modifyTermParameterValueForSession(URL, Session.SESSION_2);

        assertEquals(URL_WITH_TERM_1990_SESSION_2, URL);
    }

    @Test
    public void testGenerateURLForSession2SubjectCOSCTerm1990() {
        String URL = URLBuilder.createURLForTermAndSubject(Term.SUMMER_2016, Subject.COSC);
        URL = URLBuilder.modifyTermParameterValueForSession(URL, Session.SESSION_2);

        assertEquals(URL_WITH_TERM_1990_SUBJECT_COSC_SESSION_2, URL);
    }

    @Test
    public void testGenerateURLForSessionMinSubjectCOSCTerm2010Page2() {
        String URL = URLBuilder.createURLForTermAndSubject(Term.SPRING_2017, Subject.COSC);
        URL = URLBuilder.incrementPageNumber(URL);
        URL = URLBuilder.modifyTermParameterValueForSession(URL, Session.MINI_SESSION);

        assertEquals(URL_WITH_TERM_2010_SUBJECT_COSC_MIN_SESSION, URL);
    }

    @Test
    public void testAddSubjectParameterToURLGivenCOSCTerm2010() {
        String URL = URLBuilder.createURLForTermOnly(Term.SPRING_2017);
        URL = URLBuilder.addSubjectParameterToURL(URL, Subject.COSC);

        assertEquals(URL_WITH_TERM_2010_SUBJECT_COSC, URL);
    }

    @Test
    public void testAddSubjectParameterToURLGivenCOSCTerm2010Page2() {
        String URL = URLBuilder.createURLForTermOnly(Term.SPRING_2017);
        URL = URLBuilder.addSubjectParameterToURL(URL, Subject.COSC);
        URL = URLBuilder.changePageNumber(URL, 2);

        assertEquals(URL_WITH_TERM_2010_SUBJECT_COSC_PAGE_2, URL);
    }

}
