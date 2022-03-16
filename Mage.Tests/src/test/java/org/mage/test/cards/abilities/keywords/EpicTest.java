package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class EpicTest extends CardTestPlayerBase {

    @Test
    public void testEndlessSwarm() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.HAND, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Endless Swarm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless Swarm");

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Snake Token", 3 + 3 + 4 + 5);
        assertPermanentCount(playerA, "Forest", 8);
    }

    @Test
    public void testEndlessSwarmCopied() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 10);
        addCard(Zone.HAND, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Endless Swarm");
        addCard(Zone.HAND, playerA, "Twincast");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Endless Swarm");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Twincast", "Endless Swarm");

        setStopAt(7, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Snake Token", 2 * (3 + 3 + 4 + 5));
        assertPermanentCount(playerA, "Tropical Island", 10);
    }
}
