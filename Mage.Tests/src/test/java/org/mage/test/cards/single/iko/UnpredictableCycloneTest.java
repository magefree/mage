package org.mage.test.cards.single.iko;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class UnpredictableCycloneTest extends CardTestPlayerBase {

    @Test
    public void testCyclone() {
        // Make sure the card works normally

        addCard(Zone.LIBRARY, playerA, "Goblin Piker");
        addCard(Zone.LIBRARY, playerA, "Swamp", 10);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Unpredictable Cyclone");
        addCard(Zone.HAND, playerA, "Desert Cerodon");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Goblin Piker", 1);
    }

    @Test
    public void testLandCycle() {
        // Make sure it doesn't apply to cycling lands

        addCard(Zone.LIBRARY, playerA, "Swamp", 10);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Unpredictable Cyclone");
        addCard(Zone.HAND, playerA, "Forgotten Cave");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertHandCount(playerA, "Swamp", 1);
    }

    @Test
    public void testModifiedDraw() {
        // If a draw is increased, the ability will apply additional times

        addCard(Zone.LIBRARY, playerA, "Goblin Piker", 2);
        addCard(Zone.LIBRARY, playerA, "Swamp", 10);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Unpredictable Cyclone");
        addCard(Zone.BATTLEFIELD, playerA, "Thought Reflection");
        addCard(Zone.HAND, playerA, "Desert Cerodon");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        setChoice(playerA, "Thought Reflection"); // apply doubling first

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Goblin Piker", 2);
    }

    @Test
    public void testModifiedDraw2() {
        // Make sure the effect works with multiple stacked replacement effects

        addCard(Zone.LIBRARY, playerA, "Goblin Piker", 4);
        addCard(Zone.LIBRARY, playerA, "Swamp", 10);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Unpredictable Cyclone");
        addCard(Zone.BATTLEFIELD, playerA, "Thought Reflection", 2);
        addCard(Zone.HAND, playerA, "Desert Cerodon");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        setChoice(playerA, "Thought Reflection", 3); // apply doubling first

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Goblin Piker", 4);
    }

    @Test
    public void testStolenDraw() {
        // if your opponent cycles a card and you steal that draw with Notion Thief, the draw will still be replaced

        addCard(Zone.LIBRARY, playerA, "Goblin Piker", 1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 10);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Unpredictable Cyclone");
        addCard(Zone.HAND, playerB, "Desert Cerodon");
        addCard(Zone.HAND, playerA, "Plagiarize");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plagiarize", playerB);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cycling");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Goblin Piker", 1);
    }
}
