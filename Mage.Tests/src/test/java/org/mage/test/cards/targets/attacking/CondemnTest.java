package org.mage.test.cards.targets.attacking;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayratn
 */
public class CondemnTest extends CardTestPlayerBase {

    @Test
    public void testIllegalTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Condemn");
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        checkPlayableAbility("No valid target", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Condemn", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testLegalTarget() {
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.HAND, playerB, "Condemn");
        addCard(Zone.BATTLEFIELD, playerA, "Sejiri Merfolk");

        attack(1, playerA, "Sejiri Merfolk");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, "Condemn", "Sejiri Merfolk");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        assertPermanentCount(playerA, "Sejiri Merfolk", 0);
        assertLife(playerB, 20);
        assertLife(playerA, 21);

        // check was put on top
        Assert.assertEquals(72, currentGame.getPlayer(playerA.getId()).getLibrary().size());
    }
}
