package org.mage.test.cards.single.ths;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * @author xenohedron
 */
public class GrayMerchantOfAsphodelTest extends CardTestMultiPlayerBase {

    @Test
    public void testDevotionLifeDrain() {
        String gary = "Gray Merchant of Asphodel";
        // When Gray Merchant of Asphodel enters the battlefield, each opponent loses X life,
        // where X is your devotion to black. You gain life equal to the life lost this way.
        addCard(Zone.HAND, playerA, gary, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gary); // 2 life lost, total 8 gained

        checkLife("", 1, PhaseStep.BEGIN_COMBAT, playerB, 18);
        checkLife("", 1, PhaseStep.BEGIN_COMBAT, playerC, 20); // not in range
        checkLife("", 1, PhaseStep.BEGIN_COMBAT, playerD, 18);
        checkLife("", 1, PhaseStep.BEGIN_COMBAT, playerA, 24);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, gary); // 4 life lost, total 8 gained

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 14);
        assertLife(playerC, 20);
        assertLife(playerD, 14);
        assertLife(playerA, 32);

        assertPermanentCount(playerA, gary, 2);

    }
}
