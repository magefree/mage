package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LoreholdTheHistorianTest extends CardTestPlayerBase {

    @Test
    public void testInstantDrawnFirstGetsMiracleTwo() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Lorehold, the Historian");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.LIBRARY, playerA, "Memnite", 3);
        addCard(Zone.LIBRARY, playerB, "Memnite", 3);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt");
        skipInitShuffling();

        setChoice(playerB, true);
        addTarget(playerB, playerA);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 17);
        assertHandCount(playerB, "Lightning Bolt", 0);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }

    @Test
    public void testCreatureDrawnFirstDoesNotGainMiracle() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Lorehold, the Historian");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.LIBRARY, playerA, "Memnite", 3);
        addCard(Zone.LIBRARY, playerB, "Memnite", 3);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");
        skipInitShuffling();

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
    }

    @Test
    public void testOpponentUpkeepDiscardThenDraw() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Lorehold, the Historian");
        addCard(Zone.HAND, playerA, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        skipInitShuffling();

        setChoice(playerA, true);
        setChoice(playerA, "Memnite");

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Memnite", 1);
        assertHandCount(playerA, "Grizzly Bears", 1);
    }
}
