package mage.client.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static mage.client.MageFrame.isChristmasTime;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChristmasTest {

    private Date getDate(int Year, int Month, int Day){
        Calendar cal = new GregorianCalendar(Year, Month - 1, Day);
        cal.add(Calendar.HOUR, 10);
        return cal.getTime();
    }

    @Test
    public void testChristmasDays() throws Exception {
        // Christmas from 15 december to 15 january
        assertFalse(isChristmasTime(getDate(2017, 11, 1)));
        assertFalse(isChristmasTime(getDate(2017, 11, 15)));
        assertFalse(isChristmasTime(getDate(2017, 11, 30)));
        assertFalse(isChristmasTime(getDate(2017, 12, 1)));
        assertFalse(isChristmasTime(getDate(2017, 12, 14)));
        assertTrue(isChristmasTime(getDate(2017, 12, 15)));
        assertTrue(isChristmasTime(getDate(2017, 12, 16)));
        assertTrue(isChristmasTime(getDate(2017, 12, 31)));
        assertTrue(isChristmasTime(getDate(2018, 1, 1)));
        assertTrue(isChristmasTime(getDate(2018, 1, 14)));
        assertTrue(isChristmasTime(getDate(2018, 1, 15)));
        assertFalse(isChristmasTime(getDate(2018, 1, 16)));
        assertFalse(isChristmasTime(getDate(2018, 1, 31)));
        assertFalse(isChristmasTime(getDate(2018, 2, 1)));
        assertFalse(isChristmasTime(getDate(2018, 12, 1)));
        assertTrue(isChristmasTime(getDate(2018, 12, 20)));
        assertTrue(isChristmasTime(getDate(2019, 1, 10)));
        assertFalse(isChristmasTime(getDate(2019, 1, 25)));
    }

}
