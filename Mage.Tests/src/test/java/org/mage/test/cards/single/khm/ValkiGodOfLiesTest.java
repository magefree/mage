package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ValkiGodOfLiesTest extends CardTestPlayerBase {

    @Test
    public void ephmerateTest() {
        removeAllCardsFromLibrary(playerB);

        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 7);
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Valki, God of Lies");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerB, "Ephemerate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tibalt, Cosmic Impostor", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: Exile the top card of each player's library.");
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Plains");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Ephemerate", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Tibalt, Cosmic Impostor", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Ephemerate", 1);
    }
}
