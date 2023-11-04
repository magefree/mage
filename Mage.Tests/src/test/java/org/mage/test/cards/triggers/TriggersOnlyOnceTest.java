package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class TriggersOnlyOnceTest extends CardTestPlayerBase {

    // Reported bug #11385

    @Test
    public void testTriggersOnlyOnce() {
        String doctor = "The Sixth Doctor";
        // Whenever you cast a historic spell, copy it, except the copy isn't legendary. This ability triggers only once each turn.
        String clara = "Clara Oswald";
        // If a triggered ability of a Doctor you control triggers, that ability triggers an additional time.
        String compass = "Navigator's Compass"; // gain 3 life on ETB

        addCard(Zone.BATTLEFIELD, playerA, doctor);
        addCard(Zone.BATTLEFIELD, playerA, clara);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.HAND, playerA, compass);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, compass);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 3 + 3);
        assertPermanentCount(playerA, compass, 2);

    }

}
