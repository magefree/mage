package org.mage.test.utils;

import mage.util.DebugUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
        Assertions.assertTrue(resCurrent.startsWith("secondMethod"), "must find secondMethod, but get " + resCurrent);
        Assertions.assertTrue(resPrev.startsWith("firstMethod"), "must find firstMethod, but get " + resPrev);
        Assertions.assertTrue(resPrevPrev.startsWith("test_StackTraceWithSourceName"), "must find test_StackTraceWithSourceName, but get " + resPrevPrev);
    }

    @Test
    public void test_StackTraceWithSourceName() {
        firstMethod();
    }
}
