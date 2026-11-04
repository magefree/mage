package org.mage.test.cards.single.dmc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class JasmineBorealOfTheSevenTest extends CardTestPlayerBase {

    // Creatures you control with no abilities can't be blocked by creatures with abilities.
    private static final String jasmine = "Jasmine Boreal of the Seven";
    // ({S} can be paid with one mana from a snow permanent.) - reminder text only, no abilities
    private static final String golem = "Icehide Golem"; // 2/2
    private static final String bears = "Grizzly Bears"; // 2/2, no abilities
    private static final String goblin = "Raging Goblin"; // 1/1, haste

    @Test
    public void test_VanillaAttacker_BlockedByCreatureWithAbilities_Illegal() {
        addCard(Zone.BATTLEFIELD, playerA, jasmine);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerB, goblin);

        attack(1, playerA, bears);
        block(1, playerB, goblin, bears);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerB, goblin, 1);
    }

    @Test
    public void test_ReminderTextOnly_BlockedByCreatureWithAbilities_Illegal() {
        addCard(Zone.BATTLEFIELD, playerA, jasmine);
        addCard(Zone.BATTLEFIELD, playerA, golem);
        addCard(Zone.BATTLEFIELD, playerB, goblin);

        attack(1, playerA, golem);
        block(1, playerB, goblin, golem);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerB, goblin, 1);
    }

    @Test
    public void test_ReminderTextOnly_BlockedByCreatureWithNoAbilities_Legal() {
        addCard(Zone.BATTLEFIELD, playerA, jasmine);
        addCard(Zone.BATTLEFIELD, playerA, golem);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        attack(1, playerA, golem);
        block(1, playerB, bears, golem);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, golem, 1);
        assertGraveyardCount(playerB, bears, 1);
    }
}
