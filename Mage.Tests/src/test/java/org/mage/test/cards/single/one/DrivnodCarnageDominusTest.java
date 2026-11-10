package org.mage.test.cards.single.one;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DrivnodCarnageDominusTest extends CardTestPlayerBase {

    @Test
    public void test_OneOrMoreCreaturesDie_TriggersAdditionalTime() {
        // If a creature dying causes a triggered ability of a permanent you control to trigger,
        // that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Drivnod, Carnage Dominus");
        //
        // Whenever one or more creatures die, put a rev counter on Chainsaw.
        addCard(Zone.BATTLEFIELD, playerA, "Chainsaw");
        //
        // Each player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Innocent Blood"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        // two creatures die at once - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Innocent Blood");
        setChoice(playerA, "Grizzly Bears");
        setChoice(playerB, "Grizzly Bears");
        setChoice(playerA, "Whenever one or more creatures die"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Chainsaw", CounterType.REV, 2);
    }

    @Test
    public void test_DiesAtSameTimeAsOtherCreatures_TriggersAdditionalTime() {
        // If a creature dying causes a triggered ability of a permanent you control to trigger,
        // that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Drivnod, Carnage Dominus");
        //
        // Whenever one or more creatures die, put a rev counter on Chainsaw.
        addCard(Zone.BATTLEFIELD, playerA, "Chainsaw");
        //
        // Destroy all creatures. They can't be regenerated.
        addCard(Zone.HAND, playerA, "Wrath of God"); // {2}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        // all creatures die at once, including Drivnod - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");
        setChoice(playerA, "Whenever one or more creatures die"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Drivnod, Carnage Dominus", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Chainsaw", CounterType.REV, 2);
    }
}
