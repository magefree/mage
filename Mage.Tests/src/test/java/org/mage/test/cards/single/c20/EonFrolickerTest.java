package org.mage.test.cards.single.c20;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class EonFrolickerTest extends CardTestPlayerBase {

    @Test
    public void test_EonFrolicker_NormalPlay() {
        // https://github.com/magefree/mage/issues/6780

        // {2}{U}{U}
        // When Eon Frolicker enters the battlefield, if you cast it, target opponent takes an extra turn after this one.
        // Until your next turn, you and planeswalkers you control gain protection from that player.
        // (You and planeswalkers you control can’t be targeted, dealt damage, or enchanted by anything controlled by that player.)
        addCard(Zone.HAND, playerA, "Eon Frolicker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        // Chandra’s Fury deals 4 damage to target player or planeswalker and 1 damage to each creature that player or that planeswalker’s controller controls.
        addCard(Zone.HAND, playerB, "Chandra's Fury", 1); // {4}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eon Frolicker");
        // addTarget(playerA, playerB); Autochosen, only target

        // AI can targets only Eon Frolicker (cause A protected from B)
        checkPlayableAbility("after", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast Chandra's Fury", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Chandra's Fury");
        //addTarget(playerB, playerB);

        //setStrictChooseMode(true); // AI must choose target for fury (itself)
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Chandra's Fury", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 4);
    }
}
