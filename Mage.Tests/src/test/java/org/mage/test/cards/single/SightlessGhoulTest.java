package org.mage.test.cards.single;

import mage.Constants;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author loki
 */
public class SightlessGhoulTest extends CardTestPlayerBase {

    @Test
    public void testUndying() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Sightless Ghoul");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Constants.Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Sightless Ghoul");
        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Sightless Ghoul", 1);
        assertCounterCount("Sightless Ghoul", CounterType.P1P1, 1);
    }
}
