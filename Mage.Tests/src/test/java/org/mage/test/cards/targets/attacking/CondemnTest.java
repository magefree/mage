package org.mage.test.cards.targets.attacking;

import junit.framework.Assert;
import mage.Constants;
import mage.Constants.PhaseStep;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayratn
 */
public class CondemnTest extends CardTestPlayerBase {

    @Test
    public void testIllegalTarget() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Condemn");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        // check with illegal target
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Condemn", "Sejiri Merfolk");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        // spell shouldn't work
        assertPermanentCount(playerB, "Sejiri Merfolk", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void testLegalTarget() {
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Constants.Zone.HAND, playerB, "Condemn");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sejiri Merfolk");

        attack(1, playerA, "Sejiri Merfolk");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, "Condemn", "Sejiri Merfolk");

        setStopAt(1, Constants.PhaseStep.END_COMBAT);
        execute();
        assertPermanentCount(playerA, "Sejiri Merfolk", 0);
        assertLife(playerB, 20);
        assertLife(playerA, 21);

        // check was put on top
        Assert.assertEquals(72, currentGame.getPlayer(playerA.getId()).getLibrary().size());
    }

}
