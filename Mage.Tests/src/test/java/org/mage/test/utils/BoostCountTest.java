package org.mage.test.utils;

import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author JayDi85
 */
public class BoostCountTest {

    @Test
    public void test_BoostCountSigns() {
        Assert.assertEquals(CardUtil.getBoostCountAsStr(0, 0), "+0/+0");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(1, 0), "+1/+0");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(0, 1), "+0/+1");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(1, 1), "+1/+1");

        Assert.assertEquals(CardUtil.getBoostCountAsStr(-1, 0), "-1/-0");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(0, -1), "-0/-1");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(-1, 1), "-1/+1");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(1, -1), "+1/-1");
    }
}
