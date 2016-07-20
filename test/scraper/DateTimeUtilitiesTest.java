package scraper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static scraper.DateTimeUtilities.*;

public class DateTimeUtilitiesTest {

    @Test
    public void testTransformDateIntoSQLFormatForNullDate() throws Exception {
        assertNull(transformStringDateIntoSQLFormat(null));
    }

    @Test
    public void testTransformDateIntoSQLFormatForInvalidDate() {
        assertNull(transformStringDateIntoSQLFormat("12 12 12 12"));
    }

    @Test
    public void testTransformDateIntoSQLFormatForValidDate1() {
        assertEquals(transformStringDateIntoSQLFormat("Dec 15, 2016").toString(), "2016-12-15");
    }

    @Test
    public void testTransformDateIntoSQLFormatForValidDate2() {
        assertEquals(transformStringDateIntoSQLFormat("Aug 22, 2016").toString(), "2016-08-22");
    }


}