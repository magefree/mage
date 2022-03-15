package org.mage.test.cards.single.xln;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TreasureCoveTest extends CardTestPlayerBase {

    @Test
    public void test_CanDrawAfterSacrifice() {
        removeAllCardsFromHand(playerA);

        // {T}, Sacrifice a Treasure: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Treasure Cove", 1);
        //
        // saga: I, II — Create a Treasure token.
        addCard(Zone.BATTLEFIELD, playerA, "Forging the Tyrite Sword", 1);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treasure Token", 2); // from saga

        // sacrifice and draw
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Treasure Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treasure Token", 2 - 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}
