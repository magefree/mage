
package org.mage.test.cards.triggers.delayed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MeriekeRiBeritTest extends CardTestPlayerBase {

    /**
     * Opponent-controlled Merieke Ri Berit, and it had stolen a creature of
     * mine. When I killed Merieke, I got my creature back instead of it being
     * destroyed.
     */
    @Test
    public void testCreatureWasDestroyed() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Merieke Ri Berit doesn't untap during your untap step.
        // {T}: Gain control of target creature for as long as you control Merieke Ri Berit. When Merieke Ri Berit leaves the battlefield or becomes untapped, destroy that creature. It can't be regenerated.
        addCard(Zone.BATTLEFIELD, playerB, "Merieke Ri Berit", 1); // Creature 1/1

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Gain control", "Silvercoat Lion");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Merieke Ri Berit");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Merieke Ri Berit", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
    }

}
