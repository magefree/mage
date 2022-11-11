package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ShinenOfLifesRoarTest extends CardTestPlayerBase {

    @Test
    public void test_SingleOpponentMustBlock() {
        // All creatures able to block Shinen of Life’s Roar do so.
        addCard(Zone.BATTLEFIELD, playerA, "Shinen of Life's Roar");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2

        attack(1, playerA, "Shinen of Life's Roar", playerB);

        setStopAt(2, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Shinen of Life's Roar", 1);
    }
}
