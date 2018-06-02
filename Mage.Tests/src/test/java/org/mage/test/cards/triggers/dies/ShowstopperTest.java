
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ShowstopperTest extends CardTestPlayerBase {

    /**
     * Tests that the dies triggered ability of silvercoat lion (gained by Showstopper)
     * triggers as he dies from Ligning Bolt
     *
     */
    @Test
    public void testDiesTriggeredAbility() {
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
    public void testTwoDiesTriggeredAbilities() {
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
        addTarget(playerA, "Ornithopter");
        addTarget(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Showstopper", 2);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Ornithopter", 1);
    }

}