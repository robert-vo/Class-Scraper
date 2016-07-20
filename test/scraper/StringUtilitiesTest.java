package scraper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static scraper.StringUtilities.*;

public class StringUtilitiesTest {

    @Test
    public void testSplitByHyphenWithNullRequestFirstHalf() {
        assertEquals(splitByHyphenAndExtractHalf(null, true), "");
    }

    @Test
    public void testSplitByHyphenWithNullRequestSecondHalf() {
        assertEquals(splitByHyphenAndExtractHalf(null, false), "");
    }

    @Test
    public void testSplitByHyphenWithEmptyRoomRequestFirstHalf() {
        assertEquals(splitByHyphenAndExtractHalf("", true), "");
    }

    @Test
    public void testSplitByHyphenWithEmptyRoomRequestSecondHalf() {
        assertEquals(splitByHyphenAndExtractHalf("", false), "");
    }

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

    @Test
    public void testSplitbySpaceWithNullRequestFirstHalf() {
        assertEquals(splitBySpaceAndExtractHalf(null, true), "");
    }

    @Test
    public void testSplitbySpaceWithNullRequestSecondHalf() {
        assertEquals(splitBySpaceAndExtractHalf(null, false), "");
    }

    @Test
    public void testSplitbySpaceWithEmptyRoomRequestFirstHalf() {
        assertEquals(splitBySpaceAndExtractHalf("", true), "");
    }

    @Test
    public void testSplitbySpaceWithEmptyRoomRequestSecondHalf() {
        assertEquals(splitBySpaceAndExtractHalf("", false), "");
    }

    @Test
    public void testSplitBySpaceWithOnlyOneHalf() {
        assertEquals(splitBySpaceAndExtractHalf("R", false), "");
    }

    @Test
    public void testSplitBySpaceWithOnlyOneHalfRequestingRoom() {
        assertEquals(splitBySpaceAndExtractHalf("R", true), "R");
    }

    @Test
    public void testSplitBySpaceWithValidBuilding() {
        assertEquals(splitBySpaceAndExtractHalf("ABC 123", true), "ABC");
    }

    @Test
    public void testSplitBySpaceWithValidRoomNumber() {
        assertEquals(splitBySpaceAndExtractHalf("ABC 123", false), "123");
    }

    @Test
    public void testIsNullOrEmptyForNullString() {
        assertTrue(isNullOrEmpty(null));
    }

    @Test
    public void testIsNullOrEmptyForEmptyString() {
        assertTrue(isNullOrEmpty(""));
    }

    @Test
    public void testIsNullOrEmptyForValidString1() {
        assertFalse(isNullOrEmpty("abc"));
    }

    @Test
    public void testIsNullOrEmptyForValidString2() {
        assertFalse(isNullOrEmpty("123"));
    }

}
