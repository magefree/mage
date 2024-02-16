
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward
 */
public class ModularTest extends CardTestPlayerBase {

    /**
     * 702.42. Modular 702.42a Modular represents both a static ability and a
     * triggered ability. “Modular N” means “This permanent enters the
     * battlefield with N +1/+1 counters on it” and “When this permanent is put
     * into a graveyard from the battlefield, you may put a +1/+1 counter on
     * target artifact creature for each +1/+1 counter on this permanent.”
     * 702.42b If a creature has multiple instances of modular, each one works
     * separately.
     *
     */
    /**
     * Arcbound Bruiser Artifact Creature — Golem 0/0, 5 (5) Modular 3 (This
     * enters the battlefield with three +1/+1 counters on it. When it dies, you
     * may put its +1/+1 counters on target artifact creature.)
     *
     */
    /**
     * Arcbound Hybrid Artifact Creature — Beast 0/0, 4 (4) Haste Modular 2
     * (This enters the battlefield with two +1/+1 counters on it. When it dies,
     * you may put its +1/+1 counters on target artifact creature.)
     */
    @Test
    public void testModularEnters() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Arcbound Bruiser");
        addCard(Zone.HAND, playerA, "Arcbound Hybrid");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Bruiser", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Hybrid");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Arcbound Bruiser", 1);
        assertPermanentCount(playerA, "Arcbound Hybrid", 1);
        assertPowerToughness(playerA, "Arcbound Bruiser", 3, 3);
        assertPowerToughness(playerA, "Arcbound Hybrid", 2, 2);

    }

    @Test
    public void testModularLeaves() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Arcbound Bruiser");
        addCard(Zone.HAND, playerA, "Arcbound Hybrid");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Bruiser", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Hybrid", true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Arcbound Bruiser");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        setChoice(playerA, true);
        execute();

        assertPermanentCount(playerA, "Arcbound Bruiser", 0);
        assertPermanentCount(playerA, "Arcbound Hybrid", 1);
        assertGraveyardCount(playerA, "Arcbound Bruiser", 1);
        assertPowerToughness(playerA, "Arcbound Hybrid", 5, 5);

    }

    /**
     * My Inkmoth Nexus was in creature "form". My Arcbound Ravager died. I
     * could not transfer his counters on my Inkmoth "creature".
     */
    @Test
    public void testInkmothNexus() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Inkmoth Nexus");
        addCard(Zone.HAND, playerA, "Arcbound Ravager");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Ravager");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: {this} becomes");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Arcbound Ravager");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Arcbound Ravager", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertCounterCount("Inkmoth Nexus", CounterType.P1P1, 1);

    }

    /**
     * If a creature with modular dies due to -1/-1 counters, it still had those counters when it left
     * and therefore will still transfer them to a creature on the battlefield
     */
    @Test
    public void testMinusCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Arcbound Bruiser");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, "Puncture Blast");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Puncture Blast", "Arcbound Bruiser");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Arcbound Bruiser", 1);
        assertGraveyardCount(playerA, "Puncture Blast", 1);
        assertCounterCount("Memnite", CounterType.P1P1, 3);
    }

    /**
     * If a creature with modular dies and returns and dies again before any modular triggers resolve,
     * the modular triggers should use that creature's counters from each time it died
     * rather than the most recent time it died
     */
    @Test
    public void testReturnedAndKilledAgain() {
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 11);
        addCard(Zone.BATTLEFIELD, playerA, "Arcbound Lancer");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, "Makeshift Mannequin");
        addCard(Zone.HAND, playerA, "Puncture Blast");
        addCard(Zone.HAND, playerA, "Flame Slash");
        addCard(Zone.HAND, playerA, "Murder");

        // put three -1/-1 counters on lancer, which leaves it with one +1/+1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Puncture Blast", "Arcbound Lancer");
        setChoice(playerA, true, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // kill lancer with one +1/+1 counter on it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flame Slash", "Arcbound Lancer");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);

        // in response to modular trigger, return lancer to the battlefield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Makeshift Mannequin", "Arcbound Lancer", "When it dies");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);

        // kill lancer again with original modular trigger on the stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Arcbound Lancer", "When it dies");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Arcbound Lancer", 1);
        assertGraveyardCount(playerA, "Puncture Blast", 1);
        assertGraveyardCount(playerA, "Flame Slash", 1);
        assertGraveyardCount(playerA, "Murder", 1);
        // Memnite should have 1 counter for the first trigger and 4 from the second
        assertCounterCount("Memnite", CounterType.P1P1, 1 + 4);
    }
}
