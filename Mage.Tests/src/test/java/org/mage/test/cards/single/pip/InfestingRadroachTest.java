package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class InfestingRadroachTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.i.InfestingRadroach Infesting Radroach} {2}{B}
     * Creature — Insect Mutant
     * Flying
     * Infesting Radroach can’t block.
     * Whenever Infesting Radroach deals combat damage to a player, they get that many rad counters.
     * Whenever an opponent mills a nonland card, if Infesting Radroach is in your graveyard, you may return it to your hand.
     * 2/2
     */
    private static final String radroach = "Infesting Radroach";

    @Test
    public void test_MillSelf() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.GRAVEYARD, playerA, radroach);
        addCard(Zone.HAND, playerA, "Stitcher's Supplier"); // etb, mill 3
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.LIBRARY, playerA, "Goblin Piker", 2);
        addCard(Zone.LIBRARY, playerA, "Taiga", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stitcher's Supplier");
        // no trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 4);
    }

    @Test
    public void test_MillOpponent() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.GRAVEYARD, playerA, radroach);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.LIBRARY, playerB, "Goblin Piker", 2);
        addCard(Zone.LIBRARY, playerB, "Taiga", 1);
        addCard(Zone.LIBRARY, playerB, "Stitcher's Supplier"); // etb, mill 3

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Stitcher's Supplier");
        setChoice(playerA, "Whenever"); // 2 triggers
        setChoice(playerA, true); // yes to first, second trigger fizzles

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, 3);
        assertHandCount(playerA, radroach, 1);
    }

    @Test
    public void test_Damage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, radroach);

        attack(1, playerA, radroach, playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount(playerB, CounterType.RAD, 2);
    }
}
