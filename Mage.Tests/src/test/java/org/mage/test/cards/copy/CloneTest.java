package org.mage.test.cards.copy;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class CloneTest extends CardTestPlayerBase {

    /**
     * Tests triggers working on both sides after Clone coming onto battlefield
     */
    @Test
    public void testCloneTriggered() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Bloodgift Demon", 1);

        addCard(Constants.Zone.HAND, playerB, "Clone");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");

        setStopAt(4, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 19);

        // 1 from draw steps + 2 from Demon
        assertHandCount(playerA, 3);
        // 2 from draw steps + 1 from Demon
        assertHandCount(playerB, 3);

        assertPermanentCount(playerA, "Bloodgift Demon", 1);
        assertPermanentCount(playerB, "Bloodgift Demon", 1);
    }

    /**
     * Tests Clone is sacrificed and only one effect is turned on
     */
    @Test
    public void testCloneSacrifice() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Bloodgift Demon", 1);

        addCard(Constants.Zone.HAND, playerA, "Diabolic Edict");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Constants.Zone.HAND, playerB, "Clone");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        castSpell(3, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Diabolic Edict", playerB);

        setStopAt(4, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bloodgift Demon", 1);
        assertGraveyardCount(playerA, "Diabolic Edict", 1);
        assertPermanentCount(playerB, "Bloodgift Demon", 0);
        assertGraveyardCount(playerB, "Clone", 1);

        // 1 from draw steps + 2 from Demon
        assertHandCount(playerA, 3);
        // 2 from draw steps + no from Demon (should be sacrificed)
        assertHandCount(playerB, 2);

        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }
}
