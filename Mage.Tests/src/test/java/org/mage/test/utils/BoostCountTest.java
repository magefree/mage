package org.mage.test.utils;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
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

    @Test
    public void test_DynamicBoostCountSigns() {
        DynamicValue zero = StaticValue.get(0);
        DynamicValue plusX = GetXValue.instance;
        DynamicValue minusX = new SignInversionDynamicValue(plusX);

        Assert.assertEquals(CardUtil.getBoostCountAsStr(plusX, zero), "+X/+0");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(zero, plusX), "+0/+X");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(plusX, plusX), "+X/+X");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(minusX, zero), "-X/-0");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(zero, minusX), "-0/-X");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(minusX, plusX), "-X/+X");
        Assert.assertEquals(CardUtil.getBoostCountAsStr(plusX, minusX), "+X/-X");
    }
}
