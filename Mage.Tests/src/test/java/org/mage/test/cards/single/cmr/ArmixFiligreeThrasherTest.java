package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class ArmixFiligreeThrasherTest extends CardTestPlayerBase {

    @Test
    public void test_Playable() {
        // Whenever Armix, Filigree Thrasher attacks, you may discard a card. When you do,
        // target creature defending player controls gets -X/-X until end of turn, where X is the number
        // of artifacts you control plus the number of artifact cards in your graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Armix, Filigree Thrasher", 1);
        addCard(Zone.HAND, playerA, "Alpha Myr", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Kitesail Corsair", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1); // 2/2

        // attack and discard to kill bear
        attack(1, playerA, "Armix, Filigree Thrasher", playerB);
        setChoice(playerA, true); // pay discard cost
        setChoice(playerA, "Alpha Myr");
        addTarget(playerA, "Grizzly Bears"); // target for trigger (must kill)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 0);
    }
}
