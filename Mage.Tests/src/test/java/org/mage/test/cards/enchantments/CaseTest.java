package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CaseTest extends CardTestPlayerBase {

    @Test
    public void test_CaseOfTheBurningMasks() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Aminatou, the Fateshifter");
        addCard(Zone.HAND, playerA, "Case of the Burning Masks");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.HAND, playerA, "Impact Tremors");
        addCard(Zone.HAND, playerA, "Goblin Chainwhirler");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Case of the Burning Masks");

        checkStackSize("case is not solved", 1, PhaseStep.END_TURN, playerA, 0);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Impact Tremors");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Chainwhirler");
        setChoice(playerA, "Whenever"); // Choose trigger order

        checkStackObject("case is solved", 3, PhaseStep.END_TURN, playerA, "<i>To solve", 1);

        // Activate Aminatou, the Fateshifter ability to put Lightning Bolt on top of library
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");
        addTarget(playerA, "Lightning Bolt");
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN, playerA);
        // Activate Case of the Burning Masks "Solved" ability
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Solved");
        setChoice(playerA, "Lightning Bolt");
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Case of the Burning Masks", 0);
        assertLife(playerB, 9);
    }

    @Test
    public void test_CaseOfTheCrimsonPulse() {
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 3 + 7);
        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Case of the Crimson Pulse");
        addCard(Zone.HAND, playerA, "Wit's End");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Case of the Crimson Pulse");
        setChoice(playerA, "Mountain");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkGraveyardCount("mountain in graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain", 1);
        checkStackSize("case is not solved", 1, PhaseStep.END_TURN, playerA, 0);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Wit's End", playerA);

        checkStackObject("case is solved", 3, PhaseStep.END_TURN, playerA, "<i>To solve", 1);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.UPKEEP);
        execute();

        assertHandCount(playerA, 2);
    }

    @Test
    public void test_CaseOfTheLockedHothouse() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Case of the Locked Hothouse");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, "Griptide");

        checkStackSize("case is not solved", 1, PhaseStep.END_TURN, playerA, 0);

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");

        checkStackObject("case is solved", 3, PhaseStep.END_TURN, playerA, "<i>To solve", 1);

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Griptide", "Llanowar Elves");
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
