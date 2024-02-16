package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */

public class ShowstopperTest extends CardTestPlayerBase {

    /**
     * Tests that the dies triggered ability of silvercoat lion (gained by Showstopper)
     * triggers as he dies from Lightning Bolt
     */
    @Test
    public void test_OneTrigger() {
        // Showstopper   Instant  {1}{B}{R}
        // Until end of turn, creatures you control gain "When this creature dies, it deals 2 damage to target creature an opponent controls."
        addCard(Zone.HAND, playerA, "Showstopper");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Showstopper");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");
        addTarget(playerA, "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Showstopper", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Ornithopter", 1);
    }

    /**
     * Test if Showstopper is called twice
     */

    @Test
    public void test_TwoTriggers() {
        // Showstopper   Instant  {1}{B}{R}
        // Until end of turn, creatures you control gain "When this creature dies, it deals 2 damage to target creature an opponent controls."
        addCard(Zone.HAND, playerA, "Showstopper", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Showstopper");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Showstopper");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerA, "When {this} dies"); // choose from two triggers
        addTarget(playerA, "Ornithopter");
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Showstopper", 2);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Ornithopter", 1);
    }

    @Test
    public void test_TwoTriggersAndCopies() {
        // Showstopper   Instant  {1}{B}{R}
        // Until end of turn, creatures you control gain "When this creature dies, it deals 2 damage to target creature an opponent controls."
        addCard(Zone.HAND, playerA, "Showstopper", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Alchemist's Apprentice", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton", 1);
        //
        // When you next cast an instant spell, cast a sorcery spell, or activate a loyalty ability this turn, copy that spell or ability twice.
        // You may choose new targets for the copies.
        addCard(Zone.HAND, playerA, "Repeated Reverberation", 1); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // first spell
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 2);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Showstopper");

        // prepare copy
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Repeated Reverberation");

        // second spell with 2x copy
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Showstopper");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");
        setChoice(playerA, "When {this} dies"); // choose from 4 triggers
        setChoice(playerA, "When {this} dies"); // choose from 4 triggers
        setChoice(playerA, "When {this} dies"); // choose from 4 triggers
        addTarget(playerA, "Ornithopter");
        addTarget(playerA, "Grizzly Bears");
        addTarget(playerA, "Alchemist's Apprentice");
        addTarget(playerA, "Augmenting Automaton");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Showstopper", 2);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Ornithopter", 1);
        assertGraveyardCount(playerB, "Alchemist's Apprentice", 1);
        assertGraveyardCount(playerB, "Augmenting Automaton", 1);
    }

}