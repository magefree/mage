package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;
import org.mage.test.serverside.base.impl.CardTestAPIImpl;

public class DoublingCubeTest extends CardTestPlayerBase {

    String cube = "Doubling Cube";
    String temple = "Eldrazi Temple";

    @Test
    public void DoublingCubeEldraziTemple() {

        addCard(Zone.BATTLEFIELD, playerA, temple);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, cube);


        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G} to your mana pool");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G} to your mana pool");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G} to your mana pool");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3},{T}:");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertManaPool(playerA);


    }
}
