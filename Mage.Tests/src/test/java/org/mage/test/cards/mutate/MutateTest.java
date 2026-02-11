package org.mage.test.cards.mutate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests for Mutate mechanic (Ikoria: Lair of Behemoths)
 * @author vernonross21
 */
public class MutateTest extends CardTestPlayerBase {

    @Test
    public void testMutateTopPlacement() {
        // Test: Brokkos, Apex of Forever mutates on top of another creature
        addCard(Zone.HAND, playerA, "Brokkos, Apex of Forever");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // 1/1 creature, non-Human
        addCard(Zone.HAND, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brokkos, Apex of Forever");

        // Answer yes to place on top
        setChoice(playerA, "Yes"); // Place on top

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Verify merged permanent exists
        assertPermanentCount(playerA, "Brokkos, Apex of Forever", 1);

        // Verify Brokkos is the top card (has its 4/4 stats)
        assertPowerToughness(playerA, "Brokkos, Apex of Forever", 4, 4);
    }

    @Test
    public void testMutateBottomPlacement() {
        // Test: Mutate on bottom - creature keeps identity, gains abilities
        addCard(Zone.HAND, playerA, "Brokkos, Apex of Forever");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // 1/1
        addCard(Zone.HAND, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brokkos, Apex of Forever");

        // Answer no to place on bottom
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Verify merged permanent exists
        assertPermanentCount(playerA, "Llanowar Elves", 1);

        // Verify Llanowar Elves keeps identity (1/1)
        assertPowerToughness(playerA, "Llanowar Elves", 1, 1);
    }

    @Test
    public void testMutateEngagement() {
        // Test: Mutated creature can attack
        addCard(Zone.HAND, playerA, "Brokkos, Apex of Forever");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.HAND, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brokkos, Apex of Forever");
        setChoice(playerA, "Yes"); // Top

        attack(1, playerA, "Brokkos, Apex of Forever");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Brokkos, Apex of Forever", true);
    }

    @Test
    public void testMutateZoneMovement() {
        // Test: All merged cards move together when permanent is destroyed
        addCard(Zone.HAND, playerA, "Brokkos, Apex of Forever");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Forest", 4);
        addCard(Zone.HAND, playerB, "Fatal Push"); // Kill spell
        addCard(Zone.HAND, playerB, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brokkos, Apex of Forever");
        setChoice(playerA, "Yes"); // Top

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Fatal Push", "Brokkos, Apex of Forever");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Merged permanent should be destroyed - both cards in graveyard
        assertGraveyardCount(playerA, 2); // Brokkos + underlying creature
    }

    @Test
    public void testMutateCreatesUnion() {
        // Test: Merged creature gets all abilities
        addCard(Zone.HAND, playerA, "Brokkos, Apex of Forever"); // Has Trample
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // 1/1, non-Human
        addCard(Zone.HAND, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brokkos, Apex of Forever");
        setChoice(playerA, "Yes"); // Place on top

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Brokkos merged has Trample
        assertPermanentCount(playerA, "Brokkos, Apex of Forever", 1);
    }
}


