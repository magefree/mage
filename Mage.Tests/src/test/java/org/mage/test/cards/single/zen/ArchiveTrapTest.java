package org.mage.test.cards.single.zen;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ArchiveTrapTest extends CardTestPlayerBase {

    @Test
    public void test_CostReductionOnLibrarySearched() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 13);
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 1);

        // If an opponent searched their library this turn, you may pay {0} rather than pay this spell’s mana cost.
        // Target opponent mills thirteen cards.
        addCard(Zone.HAND, playerB, "Archive Trap"); // {3}{U}{U}
        //
        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        addCard(Zone.HAND, playerA, "Beneath the Sands"); //  {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // search for land
        checkPlayableAbility("can't cast before", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Archive Trap", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Beneath the Sands");
        addTarget(playerA, "Swamp");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after search", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp", 1);

        // must able to cast trap for {0}
        checkPlayableAbility("must able to cast", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast Archive Trap", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Archive Trap");
        setChoice(playerB, true); // use alternative cost
        addTarget(playerB, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerA, 15 - 1 - 13); // -1 by search, -13 by trap's mill
    }
}
