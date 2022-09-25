package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class TargetStackObjectByAITest extends CardTestPlayerBaseWithAIHelps {

    // only PlayerA is AI controlled
    // use case: java.lang.IllegalStateException: Target wasn't handled. class:class mage.target.TargetStackObject

    @Test
    public void test_TargetStack_Manual() {
        // {U}, {T}, Discard a card: Counter target spell or ability that targets a creature.
        addCard(Zone.BATTLEFIELD, playerB, "Diplomatic Escort", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, "Swamp", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // A attack and B response

        // attack creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        // counter it
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{U}, {T}, Discard a card", "Lightning Bolt", "Lightning Bolt");
        setChoice(playerB, "Swamp"); // discard

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertTapped("Diplomatic Escort", true);
    }

    @Test
    public void test_TargetStack_ChooseByAI() {
        // {U}, {T}, Discard a card: Counter target spell or ability that targets a creature.
        addCard(Zone.BATTLEFIELD, playerB, "Diplomatic Escort", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, "Swamp", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // A attack and B response

        // attack creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        // counter it
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{U}, {T}, Discard a card"); // AI choose target
        //setChoice(playerB, "Swamp"); // discard

        setStopAt(1, PhaseStep.END_TURN);
        //setStrictChooseMode(true); // AI must choose
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertTapped("Diplomatic Escort", true);
    }

    @Test
    public void test_TargetStack_PlayByAI() {
        // {U}, {T}, Discard a card: Counter target spell or ability that targets a creature.
        addCard(Zone.BATTLEFIELD, playerB, "Diplomatic Escort", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, "Swamp", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // A attack and B response

        // attack creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        // AI must counter it
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true); // AI play with full simulation
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertTapped("Diplomatic Escort", true);
    }
}
