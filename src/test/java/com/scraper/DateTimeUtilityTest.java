package com.scraper;

import org.junit.Test;

import static com.scraper.main.util.DateTimeUtility.transformStringDateIntoSQLFormat;
import static com.scraper.main.util.DateTimeUtility.transformStringTimeIntoSQLFormat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DateTimeUtilityTest {

    @Test
    public void testTransformDateIntoSQLFormatForNullDate() {
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

    @Test
    public void testTransformTimeIntoSQLFormatForNullTime() {
        assertNull(transformStringTimeIntoSQLFormat(null));
    }

    @Test
    public void testTransformTimeIntoSQLFormatForInvalidTime() {
        assertNull(transformStringTimeIntoSQLFormat("12 12 12 12"));
    }

    @Test
    public void testTransformTimeIntoSQLFormatForValidMidnightTime() {
        assertEquals(transformStringTimeIntoSQLFormat("00:00 A.M.").toString(), "00:00:00");
    }


    @Test
    public void testTransformTimeIntoSQLFormatForValidAMTime() {
        assertEquals(transformStringTimeIntoSQLFormat("09:00 A.M.").toString(), "09:00:00");
    }

    @Test
    public void testTransformTimeIntoSQLFormatForValidNoonTime() {
        assertEquals(transformStringTimeIntoSQLFormat("12:00 P.M.").toString(), "12:00:00");
    }

    @Test
    public void testTransformTimeIntoSQLFormatForValidPMTime() {
        assertEquals(transformStringTimeIntoSQLFormat("5:00 P.M.").toString(), "17:00:00");
    }


}