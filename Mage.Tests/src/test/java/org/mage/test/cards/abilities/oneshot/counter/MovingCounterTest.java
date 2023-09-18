
package org.mage.test.cards.abilities.oneshot.counter;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MovingCounterTest extends CardTestPlayerBase {

    /**
     * I'm having an issue when using Bioshift to move only a portion of
     * counters to another creature. When I attempt to do this, it moves all of
     * the counters (and in some cases with my Simic deck) kills the creature.
     */
    @Test
    public void testCantBeCounteredNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Move any number of +1/+1 counters from target creature onto another target creature with the same controller.
        addCard(Zone.HAND, playerA, "Bioshift", 1);

        // Protean Hydra enters the battlefield with X +1/+1 counters on it.
        // If damage would be dealt to Protean Hydra, prevent that damage and remove that many +1/+1 counters from it.
        // Whenever a +1/+1 counter is removed from Protean Hydra, put two +1/+1 counters on it at the beginning of the next end step.
        addCard(Zone.HAND, playerA, "Protean Hydra", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Protean Hydra");
        setChoice(playerA, "X=4");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bioshift", "Protean Hydra^Silvercoat Lion");
        setChoice(playerA, "X=2");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Bioshift", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4); // added 2 counters

        assertPermanentCount(playerA, "Protean Hydra", 1);
        assertPowerToughness(playerA, "Protean Hydra", 6, 6); // started with 4, removed 2, added 4 at end = 6

    }

    /**
     * I'm having an issue when using Bioshift to move only a portion of
     * counters to another creature. When I attempt to do this, it moves all of
     * the counters (and in some cases with my Simic deck) kills the creature.
     */
    @Test
    public void testFateTransfer() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        // Noxious Hatchling enters the battlefield with four -1/-1 counters on it.
        // Wither (This deals damage to creatures in the form of -1/-1 counters.)
        // Whenever you cast a black spell, remove a -1/-1 counter from Noxious Hatchling.
        // Whenever you cast a green spell, remove a -1/-1 counter from Noxious Hatchling.
        addCard(Zone.HAND, playerA, "Noxious Hatchling", 1);// 6/6
        addCard(Zone.BATTLEFIELD, playerA, "Ruin Processor", 1); // Creature 7/8

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // Move all counters from target creature onto another target creature.
        addCard(Zone.HAND, playerB, "Fate Transfer", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Noxious Hatchling");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Fate Transfer", "Noxious Hatchling^Ruin Processor");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Fate Transfer", 1);

        assertPowerToughness(playerA, "Noxious Hatchling", 6, 6);

        assertPowerToughness(playerA, "Ruin Processor", 3, 4);

    }

    /**
     * The card Leech Bonder (or the token mechanic) doesn't seem to work quite
     * as intended. If moving a -1/-1 counter from the Leech Bonder onto an
     * enemy creature with 1/1 the creature stays as a 1/1 with the token being
     * displayed on it. Going by the rules the creature should have 0/0 and thus
     * be put into the graveyard.
     */
    @Test
    public void testLeechBonder() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Leech Bonder enters the battlefield with two -1/-1 counters on it.
        // {U}, {untap}: Move a counter from target creature onto another target creature.
        addCard(Zone.HAND, playerA, "Leech Bonder", 1);// Creature 3/3 - {2}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Ley Druid", 1); // 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Leech Bonder");

        attack(3, playerA, "Leech Bonder");

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{U},", "Leech Bonder");
        // Ley Druid auto-chosen since only target

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 19);

        assertGraveyardCount(playerB, "Ley Druid", 1);
        assertPowerToughness(playerA, "Leech Bonder", 2, 2);
    }
}
