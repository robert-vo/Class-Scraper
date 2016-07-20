package scraper;

import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static scraper.ScrapeElements.*;

public class ScrapeElementsNullTest {
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

}
