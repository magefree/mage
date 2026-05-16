package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class NaktamunLorespinnerTest extends CardTestPlayerBase {

    @Test
    public void testLowHandCountPreparesWheelOfFortune() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Naktamun Lorespinner");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Island", 8);
        addCard(Zone.LIBRARY, playerB, "Swamp", 7);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Wheel of Fortune");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Shock", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerA, 7);
        assertHandCount(playerB, 7);
        assertGraveyardCount(playerA, "Wheel of Fortune", 0);
    }
}
