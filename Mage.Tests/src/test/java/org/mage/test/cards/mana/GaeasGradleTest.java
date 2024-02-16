package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GaeasGradleTest extends CardTestPlayerBase {

    // {T}: Add {G} for each creature you control.
    private final String cradle = "Gaea's Cradle";
    private final String bears = "Grizzly Bears";
    private final String thopter = "Ornithopter";

    @Test
    public void testGradle(){
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, cradle);
        addCard(Zone.BATTLEFIELD, playerA, bears, 2);
        addCard(Zone.BATTLEFIELD, playerB, thopter);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,"{T}: ");

        checkManaPool("gaeas cradle ",1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 2);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

    }
}
