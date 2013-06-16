package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Bloodgift Demon", 1);

        addCard(Zone.HAND, playerB, "Clone");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");

        setStopAt(4, PhaseStep.END_TURN);
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
        addCard(Zone.BATTLEFIELD, playerA, "Bloodgift Demon", 1);

        addCard(Zone.HAND, playerA, "Diabolic Edict");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Zone.HAND, playerB, "Clone");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Diabolic Edict", playerB);

        setStopAt(4, PhaseStep.END_TURN);
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

    @Test
    public void testCard3() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, "Public Execution");
        addCard(Zone.HAND, playerA, "Clone");

        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Public Executio", "Llanowar Elves");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Clone");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Llanowar Elves", 0);
        assertPowerToughness(playerB, "Craw Wurm", 4, 4);
        assertPowerToughness(playerA, "Craw Wurm", 6, 4);
    }
}
