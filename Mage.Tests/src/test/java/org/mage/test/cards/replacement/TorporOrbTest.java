package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Torpor Orb");
        addCard(Zone.HAND, playerA, "Wall of Omens");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wall of Omens");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Wall of Omens", 1);
        assertPermanentCount(playerA, "Torpor Orb", 1);
        assertHandCount(playerA, 0);
    }

}
