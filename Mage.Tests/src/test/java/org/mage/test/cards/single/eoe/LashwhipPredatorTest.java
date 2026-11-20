package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class LashwhipPredatorTest extends CardTestPlayerBase {

    @Test
    public void test_CostReduced() {
        // This spell costs {2} less to cast if your opponents control three or more creatures.
        addCard(Zone.HAND, playerA, "Lashwhip Predator"); // {4}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6 - 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 3); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lashwhip Predator");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Lashwhip Predator", 1);
    }

    @Test
    public void test_CostNotReduced() {
        // This spell costs {2} less to cast if your opponents control three or more creatures.
        addCard(Zone.HAND, playerA, "Lashwhip Predator"); // {4}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lashwhip Predator");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Lashwhip Predator", 1);
        assertTappedCount("Forest", true, 6);
    }
}
