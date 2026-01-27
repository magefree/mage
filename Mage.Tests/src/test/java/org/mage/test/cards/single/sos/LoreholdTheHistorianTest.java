package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class LoreholdTheHistorianTest extends CardTestPlayerBase {

    private static final String lorehold = "Lorehold, the Historian";
    private static final String mercy = "Angel's Mercy";
    private static final String mists = "Reach Through Mists";

    @Test
    public void testLorehold() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 1);
        addCard(Zone.BATTLEFIELD, playerA, lorehold);
        addCard(Zone.LIBRARY, playerA, mercy);
        addCard(Zone.HAND, playerA, mists);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mists);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 7);
        assertGraveyardCount(playerA, mists, 1);
        assertGraveyardCount(playerA, mercy, 1);
    }
}
