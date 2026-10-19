package org.mage.test.cards.single.hbg;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GoblinTrapfinderTest extends CardTestPlayerBase {

    @Test
    public void testDiscountHasteAndRepeatedSacrifice() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Hill Giant");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Trapfinder");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Doom Blade");
        castSpell(1, PhaseStep.UPKEEP, playerB, "Doom Blade", "Goblin Trapfinder");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        attack(1, playerA, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerB, 18);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, "Hill Giant", 1);
    }

    @Test
    public void testNoEligibleCreature() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Hill Giant");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Trapfinder");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Shock");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", "Goblin Trapfinder");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLibraryCount(playerA, "Hill Giant", 1);
        assertHandCount(playerA, 0);
    }

}
