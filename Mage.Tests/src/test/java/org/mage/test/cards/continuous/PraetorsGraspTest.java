package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class PraetorsGraspTest extends CardTestPlayerBase {

    @Test
    public void test_SimpleCast() {
        // Search target opponent’s library for a card and exile it face down. Then that player shuffles their library.
        // You may look at and play that card for as long as it remains exiled.
        addCard(Zone.HAND, playerA, "Praetor's Grasp", 1);  // {1}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.LIBRARY, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Praetor's Grasp");
        addTarget(playerA, playerB);
        addTarget(playerA, "Mountain");

        showAvaileableAbilities("after", 1, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Praetor's Grasp", 1);
    }
}
