import static org.junit.Assert.assertArrayEquals;

import org.junit.Assert;
import org.junit.Test;

public class CronExpressionParserTest {
    @Test
    public void testExpandField() {
        assertArrayEquals(new String[]{"0", "15", "30", "45"}, CronExpressionParser.expandField("*/15", 0, 59));
        assertArrayEquals(new String[]{"0"}, CronExpressionParser.expandField("0", 0, 59));
        assertArrayEquals(new String[]{"1", "15"}, CronExpressionParser.expandField("1,15", 1, 31));
        assertArrayEquals(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}, CronExpressionParser.expandField("*", 1, 12));
        assertArrayEquals(new String[]{"1", "2", "3", "4", "5"}, CronExpressionParser.expandField("1-5", 0, 6));
        assertArrayEquals(new String[]{"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"}, CronExpressionParser.expandField("*/5", 0, 59));
        assertArrayEquals(new String[]{"1", "3", "5", "7", "9"}, CronExpressionParser.expandField("1-9/2", 1, 31));}


    @Test
    public void testValidCronExpressionWithAsterisk() {
        String[] expected = {"0", "1", "2", "3", "4",
                            "5", "6", "7", "8", "9",
                            "10", "11", "12", "13", "14",
                            "15", "16", "17", "18", "19",
                            "20", "21", "22", "23", "24",
                            "25", "26", "27", "28", "29",
                            "30", "31", "32", "33", "34",
                            "35", "36", "37", "38", "39",
                            "40", "41", "42", "43", "44",
                            "45", "46", "47", "48", "49",
                            "50", "51", "52", "53", "54",
                            "55", "56", "57", "58", "59"};
        assertArrayEquals(expected, CronExpressionParser.expandField("*", 0, 59));
    }


    @Test
    public void testValidCronExpressionWithRanges() {
        String[] expected = {"0", "1", "2", "3"};
        assertArrayEquals(expected, CronExpressionParser.expandField("0-3", 0, 30));
    }

    @Test
    public void testValidCronExpressionWithSteps() {
        String[] expected = {"0", "2", "4", "6", "8", "10", "12"};
        assertArrayEquals(expected, CronExpressionParser.expandField("*/2", 0, 12));
    }

    @Test
    public void testValidCronExpressionWithLists() {
        String[] expected = {"0", "5", "10"};
        assertArrayEquals(expected, CronExpressionParser.expandField("0,5,10", 0, 12));
    }

    @Test
    public void testValidCronExpressionWithMixedPatterns() {
        String[] expected = {"0", "10", "20", "30", "40", "50"};
        assertArrayEquals(expected, CronExpressionParser.expandField("*/10", 0, 59));
    }


    @Test
    public void testInvalidCronExpression() {
        try {
            CronExpressionParser.expandField("invalid expression", 0, 59);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Test passed
        }
    }

    @Test
    public void testInvalidFieldValue() {
        try {
            CronExpressionParser.expandField("*/abc", 0, 59);
            Assert.fail("Expected NumberFormatException");
        } catch (NumberFormatException e) {
            // Test passed
        }
    }

    @Test
    public void testValidCronExpressionWithOverflowValues() {
        try {
            CronExpressionParser.expandField("60", 0, 59);
            Assert.fail("Invalid cron expression");
        } catch (IllegalArgumentException e) {
            // Test passed
        }
    }

    @Test
    public void testValidCronExpressionWithSingleField() {
        String[] expected ={"0", "1", "2", "3", "4",
                "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14",
                "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24",
                "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34",
                "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44",
                "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54",
                "55", "56", "57", "58", "59"};
        assertArrayEquals(expected, CronExpressionParser.expandField("*", 0, 59));
    }
}

