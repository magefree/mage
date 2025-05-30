package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class GlowingOneTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.g.GlowingOne Glowing One} {2}{G}
     * Creature — Zombie Mutant
     * Deathtouch
     * Whenever Glowing One deals combat damage to a player, they get four rad counters.
     * Whenever a player mills a nonland card, you gain 1 life.
     * 2/2
     */
    private static final String glowing = "Glowing One";

    @Test
    public void test_MillSelf() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, glowing);
        addCard(Zone.HAND, playerA, "Stitcher's Supplier"); // etb, mill 3
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.LIBRARY, playerA, "Goblin Piker", 2);
        addCard(Zone.LIBRARY, playerA, "Taiga", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stitcher's Supplier");
        setChoice(playerA, "Whenever"); // 2 triggers to stack.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 3);
        assertLife(playerA, 20 + 2);
    }

    @Test
    public void test_MillOpponent() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, glowing);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.LIBRARY, playerB, "Goblin Piker", 1);
        addCard(Zone.LIBRARY, playerB, "Taiga", 2);
        addCard(Zone.LIBRARY, playerB, "Stitcher's Supplier"); // etb, mill 3

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Stitcher's Supplier");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, 3);
        assertLife(playerA, 20 + 1);
    }
}
