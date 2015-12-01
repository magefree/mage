package mage.counters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Custom unit tests for {@link Counter}
 */
public class CounterTest {

    private Counter counter;

    @Before
    public void setUp() {
        counter = new Counter("test", 1);
    }


    @Test
    public void shouldIncreaseCounter() {
        // given

        // when
        counter.increase();

        // then
        assertEquals(2, counter.getCount());
        assertEquals("test", counter.getName());
    }


    @Test
    public void shouldAddMana() {
        // given

        // when
        counter.add(5);

        // then
        assertEquals(6, counter.getCount());
    }


    @Test
    public void shouldDecreaseCounter() {
        // given


        // when
        counter.decrease();

        // then
        assertEquals(0, counter.getCount());
    }


    @Test
    public void shouldNotDecreaseToLessThanZero() {
        // given

        // when
        counter.decrease();
        counter.decrease();

        // then
        assertEquals(0, counter.getCount());
    }


    @Test
    public void shouldRemoveCounters() {
        // given


        // when
        counter.remove(1);

        // then
        assertEquals(0, counter.getCount());
    }


    @Test
    public void shouldNotRemoveMoreCountersThanAvailable() {
        // given

        // when
        counter.remove(10);

        // then
        assertEquals(0, counter.getCount());
    }


    @Test
    public void shouldReturnCopy() {
        // given

        // when
        Counter copy = counter.copy();

        // then
        assertEquals(copy, counter);
        assertFalse(copy == counter);
    }


    @Test
    public void shouldCreateCounterFromCounter() {
        // given

        // when
        Counter copy = new Counter(counter);

        // then
        assertEquals(1, copy.getCount());
        assertEquals("test", copy.getName());
    }


    @Test
    public void shouldCreatDefaultCounter() {
        // given


        // when
        Counter defaultCounter = new Counter("default");

        // then
        assertEquals(1, defaultCounter.getCount());
        assertEquals("default", defaultCounter.getName());
    }
}