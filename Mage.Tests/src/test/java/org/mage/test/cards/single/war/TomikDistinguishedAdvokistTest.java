package org.mage.test.cards.single.war;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author awjackson
 */

public class TomikDistinguishedAdvokistTest extends CardTestPlayerBase {

    @Test
    public void testCanTargetOwnLands() {
        // https://github.com/magefree/mage/issues/9551
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Tomik, Distinguished Advokist");
        addCard(Zone.HAND, playerA, "Awakening of Vitu-Ghazi");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Awakening of Vitu-Ghazi");
        addTarget(playerA, "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Awakening of Vitu-Ghazi", 1);
        assertPermanentCount(playerA, "Vitu-Ghazi", 1);
    }
}
