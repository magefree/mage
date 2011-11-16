package org.mage.test.cards.targets.attacking;

import junit.framework.Assert;
import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author ayratn
 */
public class CondemnTest extends CardTestBase {

    @Test
    public void testIllegalTarget() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Condemn");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        castSpell(playerA, "Condemn");
        // check with illegal target
        addFixedTarget(playerA, "Condemn", "Sejiri Merfolk");

        execute();
        // spell shouldn't work
        assertPermanentCount(playerB, "Sejiri Merfolk", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void testLegalTarget() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Condemn");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        attack(playerB, "Sejiri Merfolk");
        castSpell(playerA, "Condemn");
        addFixedTarget(playerA, "Condemn", "Sejiri Merfolk");

        setStopOnTurn(3);
        execute();
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertLife(playerA, 20);
        assertLife(playerB, 21);
        // check was put on top
        Assert.assertEquals(currentGame.getPlayer(playerB.getId()).getLibrary().size(), 60);
    }

}
