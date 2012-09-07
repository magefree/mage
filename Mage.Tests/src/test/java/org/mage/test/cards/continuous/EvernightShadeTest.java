package org.mage.test.cards.continuous;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class EvernightShadeTest extends CardTestPlayerBase {

    /**
     * Tests boost disappeared after creature died
     */
    @Test
    public void testBoostWithUndying() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Evernight Shade");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{B}");
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{B}");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Evernight Shade");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Evernight Shade", 2, 2);
    }

}
