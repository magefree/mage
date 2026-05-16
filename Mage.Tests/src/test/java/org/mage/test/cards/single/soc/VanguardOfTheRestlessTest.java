package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

public class VanguardOfTheRestlessTest extends CardTestCommanderDuelBase {

    @Test
    public void testSpiritsScaleWithCommanderCastsFromCommandZone() {
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Vanguard of the Restless");
        addCard(Zone.BATTLEFIELD, playerA, "Selfless Spirit");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertPowerToughness(playerA, "Vanguard of the Restless", 3, 3);
        assertPowerToughness(playerA, "Selfless Spirit", 3, 2);
    }
}
