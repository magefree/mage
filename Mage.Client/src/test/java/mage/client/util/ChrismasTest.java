package mage.client.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static mage.client.MageFrame.isChrismasTime;

public class ChrismasTest {

    private Date getDate(int Year, int Month, int Day){
        Calendar cal = new GregorianCalendar(Year, Month - 1, Day);
        cal.add(Calendar.HOUR, 10);
        return cal.getTime();
    }

    @Test
    public void testChrismasDays() throws Exception {
        // chrismas from 15 december to 15 january
        Assert.assertEquals(false, isChrismasTime(getDate(2017, 11, 1)));
        Assert.assertEquals(false, isChrismasTime(getDate(2017, 11, 15)));
        Assert.assertEquals(false, isChrismasTime(getDate(2017, 11, 30)));
        Assert.assertEquals(false, isChrismasTime(getDate(2017, 12, 1)));
        Assert.assertEquals(false, isChrismasTime(getDate(2017, 12, 14)));
        Assert.assertEquals(true, isChrismasTime(getDate(2017, 12, 15)));
        Assert.assertEquals(true, isChrismasTime(getDate(2017, 12, 16)));
        Assert.assertEquals(true, isChrismasTime(getDate(2017, 12, 31)));
        Assert.assertEquals(true, isChrismasTime(getDate(2018, 1, 1)));
        Assert.assertEquals(true, isChrismasTime(getDate(2018, 1, 14)));
        Assert.assertEquals(true, isChrismasTime(getDate(2018, 1, 15)));
        Assert.assertEquals(false, isChrismasTime(getDate(2018, 1, 16)));
        Assert.assertEquals(false, isChrismasTime(getDate(2018, 1, 31)));
        Assert.assertEquals(false, isChrismasTime(getDate(2018, 2, 1)));
        Assert.assertEquals(false, isChrismasTime(getDate(2018, 12, 1)));
        Assert.assertEquals(true, isChrismasTime(getDate(2018, 12, 20)));
        Assert.assertEquals(true, isChrismasTime(getDate(2019, 1, 10)));
        Assert.assertEquals(false, isChrismasTime(getDate(2019, 1, 25)));
    }

}
