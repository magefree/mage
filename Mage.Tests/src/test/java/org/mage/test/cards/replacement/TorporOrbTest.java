package org.mage.test.cards.replacement;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Torpor Orb:
 *   Creatures entering the battlefield don't cause abilities to trigger.
 *
 * @author noxx
 */
public class TorporOrbTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Torpor Orb");
        addCard(Constants.Zone.HAND, playerA, "Wall of Omens");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Wall of Omens");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Wall of Omens", 1);
        assertPermanentCount(playerA, "Torpor Orb", 1);
        assertHandCount(playerA, 0);
    }

}
