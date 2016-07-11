import org.junit.Test;

import static org.junit.Assert.*;

public class URLBuilderTest {

    final String TERM_1990                              = "1990";
    final String TERM_1990_MIN                          = "1990-MIN";
    final String URL_WITH_TERM_1990                     = "http://classbrowser.uh.edu/classes?term=1990";
    final String URL_WITH_TERM_1990_MIN                 = "http://classbrowser.uh.edu/classes?term=1990-MIN";
    final String URL_WITH_TERM_1990_SUBJECT_COSC        = "http://classbrowser.uh.edu/classes?term=1990&subject=COSC";
    final String URL_WITH_TERM_1990_MIN_SUBJECT_COSC    = "http://classbrowser.uh.edu/classes?term=1990-MIN&subject=COSC";

    @Test
    public void testForTermEqualTo1990() {
        assertEquals(URLBuilder.createURLForTermOnly(TERM_1990), URL_WITH_TERM_1990);
    }

    @Test
    public void testForTermEqualTo1990Min() {
        assertEquals(URLBuilder.createURLForTermOnly(TERM_1990_MIN), URL_WITH_TERM_1990_MIN);
    }

    @Test
    public void testForTermEqualTo1990AndSubjectEqualToCOSC() {
        assertEquals(URLBuilder.createURLForTermAndSubject(TERM_1990, Subject.COSC.name()), URL_WITH_TERM_1990_SUBJECT_COSC);
    }

    @Test
    public void testForTermEqualTo1990MinAndSubjectEqualToCOSC() {
        assertEquals(URLBuilder.createURLForTermAndSubject(TERM_1990_MIN, Subject.COSC.name()), URL_WITH_TERM_1990_MIN_SUBJECT_COSC);
    }

}
