package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BrassInfiniscopeTest extends CardTestPlayerBase {

    @Test
    public void testDrawsAndGainsHalfX() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Brass Infiniscope");
        addCard(Zone.HAND, playerA, "Walking Ballista");
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");
        setChoice(playerA, "X=2");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Walking Ballista");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Island", 1);
        assertLife(playerA, 21);
    }
}
