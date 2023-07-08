package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ValkiGodOfLiesTest extends CardTestPlayerBase {

    private static final String valki = "Valki, God of Lies";
    private static final String kraken = "Kraken Hatchling";

    @Test
    public void testBecomeCopyOfExiledCreatureCard() {
        addCard(Zone.HAND, playerA, valki);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerB, kraken);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, valki);
        setChoice(playerA, kraken);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}: Choose a creature card");
        setChoice(playerA, "X=1");
        setChoice(playerA, kraken);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, kraken, 1);
        assertPowerToughness(playerA, kraken, 0, 4);
        assertPermanentCount(playerA, 4);
    }

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
