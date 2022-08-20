package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class EvolveTest extends CardTestPlayerBase {

    @Test
    public void testCreatureComesIntoPlay() {

        // Cloudfin Raptor gets one +1/+1 because Mindeye Drake comes into play
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Cloudfin Raptor", 1);

        addCard(Zone.HAND, playerA, "Mindeye Drake");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mindeye Drake");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Cloudfin Raptor", 1);
        assertPermanentCount(playerA, "Mindeye Drake", 1);

        assertPowerToughness(playerA, "Cloudfin Raptor", 1, 2);
        assertPowerToughness(playerA, "Mindeye Drake", 2, 5);
    }

    @Test
    public void testCreatureComesIntoPlayNoCounter() {

        // Experiment One gets no counter because Kird Ape is 1/1 with no Forest in play
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Experiment One", 1);

        addCard(Zone.HAND, playerA, "Kird Ape");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kird Ape");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Experiment One", 1);
        assertPermanentCount(playerA, "Kird Ape", 1);

        assertPowerToughness(playerA, "Experiment One", 1, 1);
        assertPowerToughness(playerA, "Kird Ape", 1, 1);
    }

    @Test
    public void testCreatureComesStrongerIntoPlayCounter() {

        // Experiment One gets a counter because Kird Ape is 2/2 with a Forest in play
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Experiment One", 1);

        addCard(Zone.HAND, playerA, "Kird Ape");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kird Ape");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Experiment One", 1);
        assertPermanentCount(playerA, "Kird Ape", 1);

        assertPowerToughness(playerA, "Experiment One", 2, 2);
        assertPowerToughness(playerA, "Kird Ape", 2, 3);
    }

    @Test
    public void testEvolveWithMasterBiomance() {

        // Experiment One gets a counter because Kird Ape is 2/2 with a Forest in play
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Experiment One", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Master Biomancer", 1);

        addCard(Zone.HAND, playerA, "Experiment One");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Experiment One");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Experiment One", 2);
        assertPermanentCount(playerA, "Master Biomancer", 1);

        // the first Experiment One get one counter from the second Experiment one that comes into play with two +1/+1 counters
        assertPowerToughness(playerA, "Experiment One", 2, 2);
        // the casted Experiment One got two counters from Master Biomancer
        assertPowerToughness(playerA, "Experiment One", 3, 3);

    }

    @Test
    public void testMultipleCreaturesComeIntoPlay() {
        // Cloudfin Raptor gets one +1/+1 because itself and other creatur return from exile
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Judge's Familiar", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Cloudfin Raptor", 1);
        addCard(Zone.HAND, playerA, "Mizzium Mortars", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);
        addCard(Zone.HAND, playerB, "Banisher Priest", 2);

        setStrictChooseMode(true);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banisher Priest");
        addTarget(playerB, "Cloudfin Raptor");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banisher Priest");
        addTarget(playerB, "Judge's Familiar");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mizzium Mortars with overload");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Banisher Priest", 0);

        assertGraveyardCount(playerB, "Banisher Priest", 2);
        assertGraveyardCount(playerA, 1);

        assertPermanentCount(playerA, "Cloudfin Raptor", 1);
        assertPermanentCount(playerA, "Judge's Familiar", 1);

        assertPowerToughness(playerA, "Cloudfin Raptor", 1, 2);

    }

    @Test
    public void testMultipleCreaturesComeIntoPlaySuddenDisappearance() {

        // Sudden Disappearance
        // Sorcery {5}{W}
        // Exile all nonland permanents target player controls. Return the exiled cards
        // to the battlefield under their owner's control at the beginning of the next end step.
        // Battering Krasis (2/1) and Crocanura (1/3) get both a +1/+1 counter each other because they come into play at the same time
        addCard(Zone.BATTLEFIELD, playerA, "Battering Krasis", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Crocanura", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);
        addCard(Zone.HAND, playerB, "Sudden Disappearance", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sudden Disappearance", playerA);
        setChoice(playerA, "Evolve"); // two triggers

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerA, 0);

        assertPermanentCount(playerA, "Battering Krasis", 1);
        assertPermanentCount(playerA, "Crocanura", 1);

        assertPowerToughness(playerA, "Battering Krasis", 3, 2);
        assertPowerToughness(playerA, "Crocanura", 2, 4);

    }

    /*
     * Renegade Krasis's ability when trigger it evolves is not triggered under
     * case.
     *
     * I control Renegade Krasis and two Ivy Lane Denizen. (Renegade Krasis and
     * one of Ivy Lane Denizen have a +1/+1 counter on it)
     * I cast Adaptive Snapjaw.
     * When it resolves, there are three abilities on going to stack,
     * Renegade Krasis's Evolve Ability, two Ivy Lane Denizen ability.
     * I take two Ivy Lane Denizen on to the stack and then Renegade Krasis's Evolve
     * Ability, so resolving Renegade Krasis's Evolve Ability is first . (Maybe
     * Ivy Lane Denizen's target is any.) When Renegade Krasis's Evolve Ability
     * resolves, +1/+1 counter is placed on it, but doesn't triggers Renegade
     * Krasis's second ability.
     */
    @Test
    public void testRenegadeKrasis() {

        // Evolve (Whenever a creature enters the battlefield under your control, if that creature has greater power or toughness
        // than this creature, put a +1/+1 counter on this creature.)
        // Whenever Renegade Krasis evolves, put a +1/+1 counter on each other creature you control with a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Renegade Krasis", 1); // {1}{G}{G} - 3/2
        // Whenever another green creature enters the battlefield under your control, put a +1/+1 counter on target creature.
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 16);

        // Whenever another green creature enters the battlefield under your control, put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerA, "Ivy Lane Denizen", 2); // {3}{G} - Creature 2/3
        // Evolve
        addCard(Zone.HAND, playerA, "Adaptive Snapjaw", 1); // {4}{G} - Creature 6/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ivy Lane Denizen");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Renegade Krasis");
        addTarget(playerA, "Ivy Lane Denizen"); // Ivy target

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ivy Lane Denizen"); // Renegade Krasis evolves
        addTarget(playerA, "Renegade Krasis"); // Ivy target
        setChoice(playerA, "Whenever another green creature"); // So +1/+1 counter from Renegade is first put onto Ivy

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Adaptive Snapjaw");
        addTarget(playerA, "Adaptive Snapjaw"); // From Ivy 1
        addTarget(playerA, "Adaptive Snapjaw"); // From Ivy 2
        setChoice(playerA, "Evolve"); // Evolve of Renegade Krasis first on the stack, so Adaptive Snapjaw gets +1/+1 from Renegade Krasis ability

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Renegade Krasis", 1);
        assertPermanentCount(playerA, "Ivy Lane Denizen", 2);
        assertPermanentCount(playerA, "Adaptive Snapjaw", 1);

        assertPowerToughness(playerA, "Adaptive Snapjaw", 9, 5); // +2 from Ivys + 1 From Renegade Krasis's Evolve
        assertPowerToughness(playerA, "Renegade Krasis", 6, 5); // +1 Evolve by Ivy and +1 from Ivy as 2nd Ivy enters +1 Evolve by Snapjaw
        assertPowerToughness(playerA, "Ivy Lane Denizen", 2, 3, Filter.ComparisonScope.Any);
        assertPowerToughness(playerA, "Ivy Lane Denizen", 5, 6, Filter.ComparisonScope.Any); // +1 from Other Ivy + 2 from Krasis

    }
}
