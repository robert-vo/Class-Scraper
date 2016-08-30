package com.scraper.main.util;

import org.junit.Test;

import static com.scraper.main.util.IntegerUtil.parseInt;
import static org.junit.Assert.assertEquals;

/**
 * Tests for IntegerUtil.
 *
 * @author Robert Vo
 */
public class IntegerUtilTest {

    @Test
    public void testParseIntForValidStringPositive() throws Exception {
        assertEquals(parseInt("10000"), 10000);
    }

    @Test
    public void testParseIntForValidStringNegative() throws Exception {
        assertEquals(parseInt("-1000"), -1000);
    }

    @Test
    public void testParseIntForInvalidString() throws Exception {
        assertEquals(parseInt("test"), 0);
    }

    @Test
    public void testParseIntForEmptyString() throws Exception {
        assertEquals(parseInt(""), 0);
    }

    @Test
    public void testParseIntForNullValue() throws Exception {
        assertEquals(parseInt(null), 0);
    }

}