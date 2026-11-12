package org.mage.test.cards.single.clu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BorosStrikeCaptainTest extends CardTestPlayerBase {

    @Test
    public void testPlayCardExiledByBattalion() {
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Boros Strike-Captain");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        attack(1, playerA, "Boros Strike-Captain");
        attack(1, playerA, "Balduvian Bears");
        attack(1, playerA, "Elite Vanguard");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertExileCount(playerA, "Grizzly Bears", 0);
    }
}
