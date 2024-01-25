package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CaseTest extends CardTestPlayerBase {

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

        checkStackObject("case is solved", 3, PhaseStep.END_TURN, playerA, "To solve", 1);

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

        checkStackObject("case is solved", 3, PhaseStep.END_TURN, playerA, "To solve", 1);

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Griptide", "Llanowar Elves");
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
