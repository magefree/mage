package org.mage.test.cards.single.mh2;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GaeasWillTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.LIBRARY, playerA, "Gaea's Will");
        addCard(Zone.HAND, playerA, "Beseech the Mirror");
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Dark Ritual");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Beseech the Mirror");
        setChoice(playerA, true);
        setChoice(playerA, "Sol Ring");
        addTarget(playerA, "Gaea's Will");
        setChoice(playerA, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dark Ritual");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Dark Ritual", 1);
    }

}
