package org.mage.test.cards.single.ulg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class NoMercyTest extends CardTestPlayerBase {

    private static final String noMercy = "No Mercy";
    // Whenever a creature deals damage to you, destroy it.

    private static final String spider = "Grappler Spider"; // 2/1

    @Test
    public void testDamageTrigger() {
        addCard(Zone.BATTLEFIELD, playerB, noMercy);
        addCard(Zone.BATTLEFIELD, playerA, spider);

        attack(1, playerA, spider, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        assertPermanentCount(playerA, spider, 0);
        assertGraveyardCount(playerA, spider, 1);
    }
}
