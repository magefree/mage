package org.mage.test.cards.single.mat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TyvarTheBellicoseTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Tyvar the Bellicose");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Llanowar Elves", 2, 2);
    }

}
