package org.mage.test.cards.single.ylci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class WingbaneVantasaurTest extends CardTestPlayerBase {

    @Test
    public void testSavageStompMode() {
        checkMode("1", "Savage Stomp", "Naturalize");
    }

    @Test
    public void testNaturalizeMode() {
        checkMode("2", "Naturalize", "Savage Stomp");
    }

    private void checkMode(String mode, String chosen, String other) {
        addCard(Zone.HAND, playerA, "Wingbane Vantasaur");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wingbane Vantasaur");
        setModeChoice(playerA, mode);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, chosen, 1);
        assertHandCount(playerA, other, 0);
        assertHandCount(playerB, chosen, 0);
    }
}
