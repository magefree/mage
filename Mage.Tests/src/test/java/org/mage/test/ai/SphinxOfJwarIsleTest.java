package org.mage.test.ai;

import junit.framework.Assert;
import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayratn
 */
public class SphinxOfJwarIsleTest extends CardTestBase {

    /**
     * Issue 381: AI Sphinx of Jwar Isle Loop
     *
     * AI will continuously loop using Sphinx's 'look at top card' ability.
     * version: 0.8.1
     *
     * Doesn't reproduce.
     */
    @Test
    public void testInfiniteLoopBug() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sphinx of Jwar Isle");
        execute();
    }
}
