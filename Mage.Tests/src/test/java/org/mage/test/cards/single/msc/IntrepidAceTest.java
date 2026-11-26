package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class IntrepidAceTest extends CardTestPlayerBase {

    @Test
    public void test_Attacking() {
        // This creature gets +2/+0 as long as it isn't attacking or blocking.
        addCard(Zone.BATTLEFIELD, playerA, "Intrepid Ace"); // 2/1

        checkPT("before combat", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Intrepid Ace", 2 + 2, 1);

        attack(1, playerA, "Intrepid Ace");
        checkPT("attacking", 1, PhaseStep.DECLARE_BLOCKERS, playerA, "Intrepid Ace", 2, 1);
        checkPT("after combat", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Intrepid Ace", 2 + 2, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Blocking() {
        // This creature gets +2/+0 as long as it isn't attacking or blocking.
        addCard(Zone.BATTLEFIELD, playerA, "Intrepid Ace"); // 2/1
        //
        addCard(Zone.BATTLEFIELD, playerB, "Phyrexian Walker"); // 0/3

        attack(2, playerB, "Phyrexian Walker");
        block(2, playerA, "Intrepid Ace", "Phyrexian Walker");
        checkPT("blocking", 2, PhaseStep.DECLARE_BLOCKERS, playerA, "Intrepid Ace", 2, 1);
        checkPT("after combat", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Intrepid Ace", 2 + 2, 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerB, "Phyrexian Walker", 2);
    }
}
