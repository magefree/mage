package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class KalitasBloodchiefOfGhetTest extends CardTestPlayerBase {
    @Test
    public void testTokenCreatedOnlyIfTargetDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Kalitas, Bloodchief of Ghet", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Rest in Peace", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre", 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}{B}{B}", "Gray Ogre");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, 5);
        assertPermanentCount(playerA, "Vampire", 0);
        assertPermanentCount(playerB, 0);
    }
}
