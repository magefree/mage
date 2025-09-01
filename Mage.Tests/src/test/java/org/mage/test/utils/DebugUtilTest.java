package org.mage.test.utils;

import mage.util.DebugUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DebugUtilTest extends CardTestPlayerBase {

    private void firstMethod() {
        secondMethod();
    }

    private void secondMethod() {
        String resCurrent = DebugUtil.getMethodNameWithSource(0, "method");
        String resPrev = DebugUtil.getMethodNameWithSource(1, "method");
        String resPrevPrev = DebugUtil.getMethodNameWithSource(2, "method");
        Assert.assertTrue("must find secondMethod, but get " + resCurrent, resCurrent.startsWith("secondMethod"));
        Assert.assertTrue("must find firstMethod, but get " + resPrev, resPrev.startsWith("firstMethod"));
        Assert.assertTrue("must find test_StackTraceWithSourceName, but get " + resPrevPrev, resPrevPrev.startsWith("test_StackTraceWithSourceName"));
    }

    @Test
    public void test_StackTraceWithSourceName() {
        firstMethod();
    }
}
