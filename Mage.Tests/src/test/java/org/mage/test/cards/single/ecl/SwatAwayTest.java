package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class SwatAwayTest extends CardTestPlayerBase {

    /**
     * Swat Away {2}{U}{U}
     * Instant
     * This spell costs {2} less to cast if a creature is attacking you.
     * The owner of target spell or creature puts it on their choice of the top or bottom of their library.
     */
    private static final String swatAway = "Swat Away";

    @Test
    public void test_CostReducedWhileOpponentCreatureAttacksYou() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, swatAway);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        attack(2, playerB, "Grizzly Bears", playerA);
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, swatAway, "Grizzly Bears");
        setChoice(playerB, true); // put it on top of library

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertLibraryCount(playerB, "Grizzly Bears", 1);
        assertLife(playerA, 20);
    }
}
