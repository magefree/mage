package mage.counters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CountersTest {
    private Counters counters;

    @Before
    public void setUp() {
        counters = new Counters();
    }

    @Test
    public void testCopyCounter() {
        // given
        counters.addCounter("test", 4);

        // when
        Counters copy = counters.copy();

        // then
        int amount = copy.getCount("test");
        assertEquals(5, amount);
    }

    @Test
    public void testRemoveCounter() {
        // given
        counters.addCounter("test1", 5);
        counters.addCounter("test2", 5);

        // when

        //then
        assertTrue(counters.removeCounter("test1", 6));
        assertFalse(counters.containsKey("test1"));
        assertTrue(counters.removeCounter("test2", 2));
        assertTrue(counters.containsKey("test2"));
        assertFalse(counters.removeCounter("test3", 5));
    }

    @Test
    public void testAddCounterWithNewCounter() {
        // given
        counters.addCounter("test1", 5);

        // when
        counters.addCounter("test1", 10);
        counters.addCounter("test2", 5);

        // then
        assertNotEquals(15, counters.getCount("test1"));
        assertEquals(16, counters.getCount("test1"));
        assertTrue(counters.containsKey("test2"));
        assertEquals(6, counters.getCount("test2"));
    }

    @Test
    public void testRemoveAllCounter() {
        // given
        counters.addCounter("test", 10);

        // when
        counters.removeAllCounters("test");

        // then
        assertFalse(counters.containsKey("test"));
    }

    @Test
    public void testGetCount() {
        // given
        counters.addCounter("test1", 5);

        // when
        int count1 = counters.getCount("test1");
        int count2 = counters.getCount("test2");

        // then
        assertEquals(0, count2);
        assertEquals(6, count1);
    }

    @Test
    public void testContainsKey() {
        // given
        counters.addCounter("test1", 5);

        // when

        // then
        assertTrue(counters.containsKey("test1"));
        assertFalse(counters.containsKey("test2"));
    }
}