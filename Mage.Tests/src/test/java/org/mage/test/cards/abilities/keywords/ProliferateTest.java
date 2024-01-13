
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ProliferateTest extends CardTestPlayerBase {

    /**
     * Volt Charge
     * {2}{R}
     * Volt Charge deals 3 damage to any target.
     * Proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
     */
    @Test
    public void testCastFromHandMovedToExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Pyromaster");  // starts with 4 loyality counters

        addCard(Zone.HAND, playerA, "Volt Charge");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volt Charge", playerB);
        setChoice(playerA, "Chandra, Pyromaster");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertGraveyardCount(playerA, "Volt Charge", 1);

        assertCounterCount("Chandra, Pyromaster", CounterType.LOYALTY, 5); // 4 + 1 from proliferate
    }

    /**
     * Counters aren't cancelling each other out. Reproducible with any creature
     * (graft and bloodthirst in my case) with a single +1/+1 counter on it,
     * with a single -1/-1 placed on it (Grim Affliction, Instill Infection,
     * etc). The counters should cancel each other out, leaving neither on the
     * creature, which they don't (though visually there aren't any counters
     * sitting on the card). Triggering proliferate at any point now (Thrumming
     * Bird, Steady Progress, etc) will give you the option to add another of
     * either counter, where you shouldn't have any as an option.
     */
    @Test
    public void testValidTargets() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        // Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerA, "Battlegrowth"); // {G}
        // Proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        // Draw a card.
        addCard(Zone.HAND, playerA, "Steady Progress"); // {U}{2}

        addCard(Zone.BATTLEFIELD, playerB, "Sporeback Troll");  // has two +1/+1 counter
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        // Put a -1/-1 counter on target creature, then proliferate.
        addCard(Zone.HAND, playerB, "Grim Affliction"); // {B}{2}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grim Affliction", "Silvercoat Lion");
        // proliferate Sporeback Troll

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Steady Progress");
        // Silvercoat Lion may not be a valid target now

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Battlegrowth", 1);
        assertGraveyardCount(playerA, "Steady Progress", 1);
        assertGraveyardCount(playerB, "Grim Affliction", 1);

        assertCounterCount("Silvercoat Lion", CounterType.P1P1, 0); // no valid target because no counter
        assertCounterCount("Sporeback Troll", CounterType.P1P1, 3); // 2 + 1 from proliferate
        assertPowerToughness(playerB, "Sporeback Troll", 3, 3);

    }

    @Test
    public void test_RipplesOfPotential() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 4 + 4 + 3 + 2);

        addCard(Zone.HAND, playerA, "Walking Ballista");
        addCard(Zone.HAND, playerA, "Chandra, Pyromaster");
        addCard(Zone.HAND, playerA, "Atraxa's Skitterfang");
        addCard(Zone.HAND, playerA, "Ripples of Potential");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Walking Ballista");
        setChoice(playerA, "X=2");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chandra, Pyromaster");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Atraxa's Skitterfang");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ripples of Potential");
        // Proliferate choice
        setChoice(playerA, "Walking Ballista^Chandra, Pyromaster^Atraxa's Skitterfang");
        // Phase out choice
        setChoice(playerA, "Walking Ballista^Chandra, Pyromaster");

        setStrictChooseMode(true);
        setChoice(playerA, false);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Walking Ballista", 0);
        assertPermanentCount(playerA, "Chandra, Pyromaster", 0);
        assertPermanentCount(playerA, "Atraxa's Skitterfang", 1);
    }
}
