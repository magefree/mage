package org.mage.test.cards.single.ala;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class BanewaspAfflictionTest extends CardTestPlayerBase {

    private static final String banewasp = "Banewasp Affliction";
    // When enchanted creature dies, that creature’s controller loses life equal to its toughness.

    private static final String murder = "Murder";
    private static final String zombie = "Walking Corpse";

    @Test
    public void testController() {
        addCard(Zone.BATTLEFIELD, playerB, zombie);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, banewasp);
        addCard(Zone.HAND, playerA, murder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, banewasp, zombie);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, zombie);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, murder, 1);
        assertGraveyardCount(playerA, banewasp, 1);
        assertGraveyardCount(playerB, zombie, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 18);

    }
}
