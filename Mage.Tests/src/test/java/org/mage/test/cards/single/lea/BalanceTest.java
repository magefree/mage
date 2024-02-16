package org.mage.test.cards.single.lea;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BalanceTest extends CardTestPlayerBase {

    @Test
    public void testBalance() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 3);
        addCard(Zone.HAND, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Balance");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 2);
        addCard(Zone.HAND, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balance");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerA, "Runeclaw Bear", 2);
        assertHandCount(playerA, "Plains", 1);
        assertHandCount(playerA, "Balance", 0);
        assertGraveyardCount(playerA, "Runeclaw Bear", 1);
        assertGraveyardCount(playerA, "Balance", 1);

        assertPermanentCount(playerB, "Swamp", 2);
        assertPermanentCount(playerB, "Runeclaw Bear", 2);
        assertHandCount(playerB, "Swamp", 1);
        assertGraveyardCount(playerB, "Swamp", 2); //1 from hand, 1 from battlefield

    }
}
