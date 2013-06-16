package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author loki
 */
public class SightlessGhoulTest extends CardTestPlayerBase {

    @Test
    public void testUndying() {
        addCard(Zone.BATTLEFIELD, playerA, "Sightless Ghoul");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Sightless Ghoul");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Sightless Ghoul", 1);
        assertCounterCount("Sightless Ghoul", CounterType.P1P1, 1);
    }
}
