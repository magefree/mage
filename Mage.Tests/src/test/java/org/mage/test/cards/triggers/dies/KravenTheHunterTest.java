package org.mage.test.cards.triggers.dies;
 
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class KravenTheHunterTest extends CardTestPlayerBase {

    @Test
    public void testKraven() {
        addCard(Zone.BATTLEFIELD, playerA, "Kraven the Hunter");
        addCard(Zone.HAND, playerA, "Fell");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fell", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Kraven the Hunter", 5, 4);
    }

}
