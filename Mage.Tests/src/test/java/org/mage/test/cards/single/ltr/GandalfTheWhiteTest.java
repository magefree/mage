package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GandalfTheWhiteTest extends CardTestPlayerBase {

    @Test
    public void test_OneOrMoreArtifactsEnter_TriggersAdditionalTime() {
        // If a legendary permanent or an artifact entering or leaving the battlefield causes a triggered ability
        // of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Gandalf the White");
        //
        // Whenever one or more tokens enter the battlefield under your control, each opponent loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Kambal, Profiteering Mayor");
        //
        // Create two 1/1 colorless Servo artifact creature tokens.
        addCard(Zone.HAND, playerA, "Servo Exhibition"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // two artifacts enter at once - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Servo Exhibition");
        setChoice(playerA, "Whenever one or more tokens you control enter"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Servo Token", 2);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_OneOrMoreArtifactsDie_TriggersAdditionalTime() {
        // If a legendary permanent or an artifact entering or leaving the battlefield causes a triggered ability
        // of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Gandalf the White");
        //
        // Whenever one or more creatures die, put a rev counter on Chainsaw.
        addCard(Zone.BATTLEFIELD, playerA, "Chainsaw");
        //
        // Each player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Innocent Blood"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        // two creatures die at once (artifact and non-artifact) - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Innocent Blood");
        setChoice(playerA, "Ornithopter");
        setChoice(playerB, "Grizzly Bears");
        setChoice(playerA, "Whenever one or more creatures die"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Ornithopter", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Chainsaw", CounterType.REV, 2);
    }

    @Test
    public void test_ArtifactDiesButNotCauseTrigger_NoAdditionalTrigger() {
        // If a legendary permanent or an artifact entering or leaving the battlefield causes a triggered ability
        // of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Gandalf the White");
        //
        // Whenever one or more other creatures you control die, put a +1/+1 counter on Vengeful Townsfolk.
        addCard(Zone.BATTLEFIELD, playerA, "Vengeful Townsfolk");
        //
        // Each player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Innocent Blood"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 1);

        // two creatures die at once, but only non-artifact causes a trigger - one trigger only
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Innocent Blood");
        setChoice(playerA, "Grizzly Bears");
        setChoice(playerB, "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Ornithopter", 1);
        assertCounterCount(playerA, "Vengeful Townsfolk", CounterType.P1P1, 1);
    }
}
