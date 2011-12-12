package org.mage.test.combat;

import junit.framework.Assert;
import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 *
 * @author ayrat
 */
public class DamageDistributionTest extends CardTestBase {

    @Test
    public void testDoubleStrike() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Warren Instigator");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Merfolk Looter");
        setLife(playerB, 4);

        execute();

        Permanent instigator = getPermanent("Warren Instigator", playerA.getId());
        Assert.assertNotNull(instigator);

        // should block and die
        assertPermanentCount(playerB, "Merfolk Looter", 0);

        // creature is blocked
        // blocker dies and second strike does nothing
        assertLife(playerB, 4);
    }

    @Test
    public void testDoubleStrikeUnblocked() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Warren Instigator");
        setLife(playerB, 4);
        execute();
        assertLife(playerB, 2);
    }

}
