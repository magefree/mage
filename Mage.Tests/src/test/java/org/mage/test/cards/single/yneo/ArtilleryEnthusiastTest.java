package org.mage.test.cards.single.yneo;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ArtilleryEnthusiastTest extends CardTestPlayerBase {

    @Test
    public void testSeekEqualManaValueIncludesLand() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Artillery Enthusiast");
        addCard(Zone.HAND, playerA, "Island");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Artillery Enthusiast");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Forest", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Island", 1);
    }

    @Test
    public void testDecliningDiscardDoesNotSeek() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Artillery Enthusiast");
        addCard(Zone.HAND, playerA, "Island");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Artillery Enthusiast");
        setChoice(playerA, false);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Island", 1);
        assertGraveyardCount(playerA, 0);
    }

}
