import org.junit.Test;

import static org.junit.Assert.*;

public class URLBuilderTest {

    final String URL_WITH_TERM_1990                 = "http://classbrowser.uh.edu/classes?term=1990";
    final String URL_WITH_TERM_2010                 = "http://classbrowser.uh.edu/classes?term=2010";
    final String URL_WITH_TERM_1990_SUBJECT_COSC    = "http://classbrowser.uh.edu/classes?term=1990&subject=COSC";
    final String URL_WITH_TERM_2010_SUBJECT_COSC    = "http://classbrowser.uh.edu/classes?term=2010&subject=COSC";

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
        assertEquals(URLBuilder.createURLForTermAndSubject(Term.SUMMER_2016, Subject.COSC.name()), URL_WITH_TERM_1990_SUBJECT_COSC);
    }

    @Test
    public void testForTermEqualToSpring2017AndSubjectEqualToCOSC() {
        assertEquals(URLBuilder.createURLForTermAndSubject(Term.SPRING_2017, Subject.COSC.name()), URL_WITH_TERM_2010_SUBJECT_COSC);
    }

}
