package scraper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static scraper.StringUtilities.*;

public class StringUtilitiesTest {

    @Test
    public void testSplitByHyphenWithOnlyOneHalf() {
        assertEquals(splitByHyphenAndExtractHalf("12:00 A.M.-", false), "");
    }

    @Test
    public void testSplitByHyphenWithOnlyOneHalfRequestingTime() {
        assertEquals(splitByHyphenAndExtractHalf("12:00 A.M.-", true), "12:00 A.M.");
    }

    @Test
    public void testSplitByHyphenWithCorrectStartTime() {
        assertEquals(splitByHyphenAndExtractHalf("09:00 A.M.-12:00 P.M.", true), "09:00 A.M.");
    }

    @Test
    public void testSplitByHyphenWithCorrectEndTime() {
        assertEquals(splitByHyphenAndExtractHalf("Sa 09:00 A.M.-12:00 P.M.", false), "12:00 P.M.");
    }

}
