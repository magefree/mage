package org.mage.test.cards.single.ybro;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CruciasTitanOfTheWavesTest extends CardTestPlayerBase {

    @Test
    public void testAmbitiousSeeksStrictlyGreater() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Hill Giant");
        addCard(Zone.BATTLEFIELD, playerA, "Crucias, Titan of the Waves");
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        setChoice(playerA, true);
        setChoice(playerA, "ambitious");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertHandCount(playerA, "Hill Giant", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
        assertPowerToughness(playerA, "Crucias, Titan of the Waves", 3, 1);
    }

    @Test
    public void testExpedientCanSeekLand() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Crucias, Titan of the Waves");
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        setChoice(playerA, true);
        setChoice(playerA, "expedient");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertHandCount(playerA, "Island", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
    }

}
