package com.scraper;

import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

import static com.scraper.main.ScrapeHTMLElements.*;
import static com.scraper.main.util.IntegerUtil.parseInt;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScrapeHTMLElementsNullTest {

    Element mockedNullElement = mock(Element.class);

    @Before
    public void setUp() {
        when(mockedNullElement.select(any())).thenReturn(null);
    }

    @Test
    public void testGetNullDescription() {
        assertEquals(getDescription(mockedNullElement), "No description available.");
    }

    @Test
    public void testGetNullStartTime() {
        assertEquals(getClassStartTime(mockedNullElement), "");
    }

    @Test
    public void testGetNullEndTime() {
        assertEquals(getClassEndTime(mockedNullElement), "");
    }

    @Test
    public void testGetNullStartDate() {
        assertEquals(getClassStartDate(mockedNullElement), "");
    }

    @Test
    public void testGetNullEndDate() {
        assertEquals(getClassEndDate(mockedNullElement), "");
    }

    @Test
    public void testParseIntForEmptyString() {
        assertEquals(parseInt(""), 0);
    }

    @Test
    public void testParseIntForNullString() {
        assertEquals(parseInt(null), 0);
    }

    @Test
    public void testParseIntForInvalidString() {
        assertEquals(parseInt("abc"), 0);
    }
}
