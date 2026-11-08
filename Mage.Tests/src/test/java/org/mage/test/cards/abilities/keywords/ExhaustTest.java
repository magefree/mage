package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ExhaustTest extends CardTestPlayerBase {

    @Test
    public void testManaExhaustDoesNotUseStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Loot, the Pathfinder");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        setChoice(playerA, "Green");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {G}");
        checkStackSize("mana exhaust doesn't use the stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkManaPool("mana exhaust added three green mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 3);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void testNonManaExhaustUsesStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Loot, the Pathfinder");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Forest", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {U}");
        checkStackSize("non-mana exhaust uses the stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, 3);
    }
}
