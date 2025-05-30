package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author JayDi85
 */

public class NadierAgentOfTheDuskenelTest extends CardTestCommanderDuelBase {

    @Test
    public void test_DieAnother() {
        addCustomEffect_TargetDestroy(playerA);

        // Whenever a token you control leaves the battlefield, put a +1/+1 counter on Nadier, Agent of the Duskenel.
        // When Nadier leaves the battlefield, create a number of 1/1 green Elf Warrior creature tokens equal to its power.
        addCard(Zone.BATTLEFIELD, playerA, "Nadier, Agent of the Duskenel", 1);
        //
        // {5}, {T}: Create a 1/1 colorless Insect artifact creature token with flying named Wasp.
        addCard(Zone.BATTLEFIELD, playerA, "The Hive", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        // prepare token
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}, {T}: Create");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wasp", 1);

        // on leaves non-token -- nothing to happen
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "The Hive");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkGraveyardCount("on non-token", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Hive", 1);
        checkStackSize("on non-token - no triggers", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        // on leaves token - must trigger
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Wasp");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("on token", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wasp", 0);
        checkPermanentCounters("on token - must trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nadier, Agent of the Duskenel", CounterType.P1P1, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_DieItself() {
        addCustomEffect_TargetDestroy(playerA, 2);

        // Whenever a token you control leaves the battlefield, put a +1/+1 counter on Nadier, Agent of the Duskenel.
        // When Nadier leaves the battlefield, create a number of 1/1 green Elf Warrior creature tokens equal to its power.
        addCard(Zone.BATTLEFIELD, playerA, "Nadier, Agent of the Duskenel", 1);
        //
        // {5}, {T}: Create a 1/1 colorless Insect artifact creature token with flying named Wasp.
        addCard(Zone.BATTLEFIELD, playerA, "The Hive", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        // prepare token
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}, {T}: Create");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wasp", 1);

        // on leaves token and itself -- must x2 triggers:
        // * one with counter to fizzle
        // * one with new token
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Wasp^Nadier, Agent of the Duskenel");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkStackSize("must triggers x2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        setChoice(playerA, "Whenever a token you control leaves"); // x2 triggers from Nadier

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "The Hive", 1);
        assertPermanentCount(playerA, "Wasp", 0);
        assertGraveyardCount(playerA, "Nadier, Agent of the Duskenel", 1);
        assertPermanentCount(playerA, "Elf Warrior Token", 3);
    }
}
