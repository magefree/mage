package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SergeantJohnBentonTest extends CardTestPlayerBase {

    @Test
    public void test_Normal() {
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);

        // Share Intelligence -- Whenever Sergeant John Benton deals combat damage to a player, 
        // you and that player each draw that many cards.
        addCard(Zone.BATTLEFIELD, playerA, "Sergeant John Benton"); // 2/4

        // attack and draw
        attack(1, playerA, "Sergeant John Benton");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // draw 2 cards due 2 damage
        assertHandCount(playerA, 2);
        assertHandCount(playerB, 2);
    }
}
