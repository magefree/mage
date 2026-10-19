package org.mage.test.cards.single.yotj;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ImpetuousLootmongerTest extends CardTestPlayerBase {

    @Test
    public void testHeistSkipsLandsAndStolenSpellMakesTappedTreasure() {
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Island", 5);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Impetuous Lootmonger");
        addCard(Zone.HAND, playerA, "Island");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Impetuous Lootmonger");
        addTarget(playerA, playerB);
        setChoice(playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Island", 1);
        assertLibraryCount(playerB, "Island", 5);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
        assertTapped("Treasure Token", true);
    }

    @Test
    public void testEmptyHandStillHeistsAndOwnSpellDoesNotTrigger() {
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Impetuous Lootmonger");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Impetuous Lootmonger");
        addTarget(playerA, playerB);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Island");
        setChoice(playerA, "Island");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Shock", playerB);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 0);
        assertLife(playerB, 18);
    }

}
