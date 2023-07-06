package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.fail;

public class GlamdringTest extends CardTestPlayerBase {
    @Test
    public void castForFreeTest() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, "In Garruk's Wake");
        addCard(Zone.HAND, playerA, "Blue Sun's Zenith");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerA, "Glamdring");
        addCard(Zone.BATTLEFIELD, playerA, "Blur Sliver"); // 2/2, Haste
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", "Blur Sliver");

        attack(1, playerA, "Blur Sliver");
        setChoice(playerA, "In Garruk's Wake");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.FIRST_COMBAT_DAMAGE);

        try {
            execute();
        }
        catch (AssertionError e) {
            // This is the error we expect to see, so pass the test
            return;
        }
        fail("Was able to pick In Garruk's Wake but it costs more than 2");
    }
}